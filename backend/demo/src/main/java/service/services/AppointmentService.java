package service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.AppointmentDto;
import service.dto.EmailDto;
import service.dto.TechnicianAppointmentDto;
import service.dto.UserAppointmentDto;
import service.entity.*;
import service.repository.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    private  AppointmentRepository appointmentRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  TechnicianProfileRepository technicianProfileRepository;
    @Autowired
    private  TypeRepository typeRepository;
    @Autowired
    private  UserProfileRepository userProfileRepository;
    @Autowired
    private EmailService emailService;

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public ResponseEntity<?> addAppointment(AppointmentDto appointmentDto) {
        UserProfile user = userProfileRepository.findByUsername(appointmentDto.getUsernameUser());
        TechnicianProfile technicianProfile = technicianProfileRepository.findByUsername(appointmentDto.getUsernameTechnician());
        Type type = typeRepository.findByNameTypeAndTechnicianProfile_Username(appointmentDto.getType(), appointmentDto.getUsernameTechnician());
        if (user == null || technicianProfile == null || type == null) {
            return new ResponseEntity<>("Technician or user or type doesn t exist", HttpStatus.BAD_REQUEST);
        } else {
            Appointment appointment = new Appointment();
            appointment.setDate(appointmentDto.getData());
            appointment.setTechnician(technicianProfile);
            appointment.setUserProfile(user);
            appointment.setType(type);
            appointment.setStatus("Pending");
            appointmentRepository.save(appointment);
            User userAux = userRepository.findByUsername(appointmentDto.getUsernameUser());
            TechnicianProfile technicianProfile1 = technicianProfileRepository.findByUsername(appointmentDto.getUsernameTechnician());
            EmailDto emailDto = new EmailDto();
            emailDto.setRecipient(userAux.getEmail());
            emailDto.setMsgBody("Hi " + userAux.getUsername() + ",\n" + "\n"+ "We received your appointment in date: "+ appointmentDto.getData()+ " for service: " + appointmentDto.getType()+ " at technician "+ technicianProfile1.getFirstName() + " "+ technicianProfile1.getLastName() + "." );
            emailDto.setSubject("Your appointment is aproved");
            new Thread(() -> {
                emailService.sendSimpleMail(emailDto);
            }).start();
            return new ResponseEntity<>("Appointment create successfull", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getUserAppointments(int id) {
        User user = userRepository.findById(id);
        List<Appointment> appointmentList = appointmentRepository.findByUserProfile_IdProfile(user.getIdUser());
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } else {
            List<UserAppointmentDto> userAppointmentDtoList = new ArrayList<>();
            for (Appointment appointment : appointmentList) {
                UserAppointmentDto userAppointmentDto = new UserAppointmentDto();
                userAppointmentDto.setDate(appointment.getDate());
                userAppointmentDto.setNameType(appointment.getType().getNameType());
                userAppointmentDto.setTechnicianEmail(appointment.getTechnician().getEmail());
                userAppointmentDto.setTechnicianPhone(appointment.getTechnician().getPhone());
                userAppointmentDto.setTechnicianLastName(appointment.getTechnician().getLastName());
                userAppointmentDto.setTechnicianFirstName(appointment.getTechnician().getFirstName());
                userAppointmentDto.setStatus(appointment.getStatus());
                userAppointmentDto.setIdAppointment(appointment.getId());
                userAppointmentDto.setPrice(appointment.getType().getPrice());
                userAppointmentDtoList.add(userAppointmentDto);
            }
            return new ResponseEntity<>(userAppointmentDtoList, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getTechnicianAppointments(int id) {
        List<Appointment> appointmentList = appointmentRepository.findByTechnician(id);
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<>(" ", HttpStatus.NO_CONTENT);
        } else {
            List<TechnicianAppointmentDto> TechnicianAppointmentDtoList = new ArrayList<>();
            for (Appointment appointment : appointmentList) {
                UserProfile userProfile = userProfileRepository.findByUsername(appointment.getUserProfile().getUsername());
                TechnicianAppointmentDto TechnicianAppointmentDto = new TechnicianAppointmentDto();
                TechnicianAppointmentDto.setDate(appointment.getDate());
                TechnicianAppointmentDto.setNameType(appointment.getType().getNameType());
                TechnicianAppointmentDto.setUserEmail(userProfile.getEmail());
                TechnicianAppointmentDto.setUserPhone(userProfile.getPhone());
                TechnicianAppointmentDto.setUserLastName(userProfile.getLastName());
                TechnicianAppointmentDto.setUserFirstName(userProfile.getFirstName());
                TechnicianAppointmentDto.setStatus(appointment.getStatus());
                TechnicianAppointmentDto.setIdAppointment(appointment.getId());
                TechnicianAppointmentDtoList.add(TechnicianAppointmentDto);
            }
            return new ResponseEntity<>(TechnicianAppointmentDtoList, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateAppointment(int id, String newStatus) {
        Optional<Appointment> existingAppointmentOptional = appointmentRepository.findById(id);
        if (existingAppointmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
        Appointment existingAppointment = existingAppointmentOptional.get();
        String oldStatus = existingAppointment.getStatus();
        existingAppointment.setStatus(newStatus);
        User userAux = userRepository.findByUsername(existingAppointment.getUserProfile().getUsername());
        EmailDto emailDto = new EmailDto();
        emailDto.setRecipient(userAux.getEmail());
        emailDto.setMsgBody("Hi " + userAux.getUsername() + ",\n" + "\n"+ "Your appointment from the date of "+ existingAppointment.getDate()+ " for the service " + existingAppointment.getType().getNameType() +" was changed successfull from "+ oldStatus + " to " + existingAppointment.getStatus() + ".");
        emailDto.setSubject("Your appointment status was changed");
        emailService.sendSimpleMail(emailDto);
        appointmentRepository.save(existingAppointment);

        return new ResponseEntity<>(existingAppointment, HttpStatus.OK);
    }

    public List<String> getFreeTimeSlots(int TechnicianId, Date data) {
        List<Appointment> TechnicianAppointments = appointmentRepository.getAppointmentsByDateAndStatus(data, TechnicianId);
        List<String> busyHours = new ArrayList<>();
        List<String> freeHours = new ArrayList<>();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        int startHour = 9;
        int endHour = 18;

        for (int hour = startHour; hour <= endHour; hour++) {
            String timeSlot = String.format("%02d:00", hour);
            freeHours.add(timeSlot);
            System.out.println(timeSlot);
        }

        for (Appointment appointment : TechnicianAppointments) {
            if (appointment.getStatus().equals("Pending") || appointment.getStatus().equals("Done") || appointment.getStatus().equals("Can`t fix")) {
                Date appointmentStart = appointment.getDate();
                String formattedTime = timeFormat.format(appointmentStart);
                busyHours.add(formattedTime);
            }
        }
        freeHours.removeAll(busyHours);
        return freeHours;
    }
}
