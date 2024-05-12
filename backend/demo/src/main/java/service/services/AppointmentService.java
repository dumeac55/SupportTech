package service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.AppointmentDto;
import service.dto.MechanicAppointmentDto;
import service.dto.UserAppointmentDto;
import service.entity.*;
import service.repository.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    private final MechanicProfileRepository mechanicProfileRepository;

    private final TypeRepository typeRepository;
    private final UserProfileRepository userProfileRepository;

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public ResponseEntity<?> addAppointment(AppointmentDto appointmentDto) {
        User user = userRepository.findByUsername(appointmentDto.getUsernameUser());
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByUsername(appointmentDto.getUsernameMechanic());
        Type type = typeRepository.findByNameType(appointmentDto.getType());
        if (user == null || mechanicProfile == null || type == null) {
            return new ResponseEntity<>("Mechanic or user or type doesn t exist", HttpStatus.BAD_REQUEST);
        } else {
            Appointment appointment = new Appointment();
            appointment.setDate(appointmentDto.getData());
            appointment.setMechanic(mechanicProfile);
            appointment.setUser(user);
            appointment.setType(type);
            appointment.setStatus("Pending");
            appointmentRepository.save(appointment);
            return new ResponseEntity<>("Appointment create successfull", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getUserAppointments(int id) {
        List<Appointment> appointmentList = appointmentRepository.findByUser(id);
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } else {
            List<UserAppointmentDto> userAppointmentDtoList = new ArrayList<>();
            for (Appointment appointment : appointmentList) {
                UserAppointmentDto userAppointmentDto = new UserAppointmentDto();
                userAppointmentDto.setDate(appointment.getDate());
                userAppointmentDto.setNameType(appointment.getType().getNameType());
                userAppointmentDto.setMechanicEmail(appointment.getMechanic().getEmail());
                userAppointmentDto.setMechanicPhone(appointment.getMechanic().getPhone());
                userAppointmentDto.setMechanicLastName(appointment.getMechanic().getLastName());
                userAppointmentDto.setMechanicFirstName(appointment.getMechanic().getFirstName());
                userAppointmentDto.setStatus(appointment.getStatus());
                userAppointmentDto.setIdAppointment(appointment.getId());
                userAppointmentDtoList.add(userAppointmentDto);
            }
            return new ResponseEntity<>(userAppointmentDtoList, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getMechanicAppointments(int id) {
        List<Appointment> appointmentList = appointmentRepository.findByMechanic(id);
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<>(" ", HttpStatus.NO_CONTENT);
        } else {
            List<MechanicAppointmentDto> mechanicAppointmentDtoList = new ArrayList<>();
            for (Appointment appointment : appointmentList) {
                UserProfile userProfile = userProfileRepository.findByUsername(appointment.getUser().getUsername());
                MechanicAppointmentDto mechanicAppointmentDto = new MechanicAppointmentDto();
                mechanicAppointmentDto.setDate(appointment.getDate());
                mechanicAppointmentDto.setNameType(appointment.getType().getNameType());
                mechanicAppointmentDto.setUserEmail(userProfile.getEmail());
                mechanicAppointmentDto.setUserPhone(userProfile.getPhone());
                mechanicAppointmentDto.setUserLastName(userProfile.getLastName());
                mechanicAppointmentDto.setUserFirstName(userProfile.getFirstName());
                mechanicAppointmentDto.setStatus(appointment.getStatus());
                mechanicAppointmentDto.setIdAppointment(appointment.getId());
                mechanicAppointmentDtoList.add(mechanicAppointmentDto);
            }
            return new ResponseEntity<>(mechanicAppointmentDtoList, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateAppointment(int id, String newStatus) {
        Optional<Appointment> existingAppointmentOptional = appointmentRepository.findById(id);
        if (existingAppointmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
        Appointment existingAppointment = existingAppointmentOptional.get();
        existingAppointment.setStatus(newStatus);
        appointmentRepository.save(existingAppointment);

        return new ResponseEntity<>(existingAppointment, HttpStatus.OK);
    }


    public List<String> getFreeTimeSlots(int mechanicId, Date data) {
        List<Appointment> mechanicAppointments = appointmentRepository.getAppointmentsByDateAndStatus(data, mechanicId);
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

        for (Appointment appointment : mechanicAppointments) {
            if (appointment.getStatus().equals("Pending") || appointment.getStatus().equals("Done")) {
                Date appointmentStart = appointment.getDate();
                String formattedTime = timeFormat.format(appointmentStart);
                busyHours.add(formattedTime);
            }
        }
        freeHours.removeAll(busyHours);
        return freeHours;
    }
}

