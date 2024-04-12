package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.AppointmentDto;
import service.dto.UserAppointmentDto;
import service.entity.*;
import service.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MechanicProfileRepository mechanicProfileRepository;

    @Autowired
    private TypeRepository typeRepository;

    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    public ResponseEntity<?> addAppointment(AppointmentDto appointmentDto){
        User user = userRepository.findByUsername(appointmentDto.getUsernameUser());
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByUsername(appointmentDto.getUsernameMechanic());
        Type type = typeRepository.findByNameType(appointmentDto.getType());
        if (user == null || mechanicProfile == null || type == null){
            return new ResponseEntity<>("Mechanic or user or type doesn t exist", HttpStatus.BAD_REQUEST);
        }
        else{
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

    public ResponseEntity<?> getUserAppointments(int id){
        List<Appointment> appointmentList = appointmentRepository.findByUser(id);
        if(appointmentList.isEmpty()){
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
        }
        else{
            List<UserAppointmentDto> userAppointmentDtoList = new ArrayList<>();
            for(Appointment appointment : appointmentList) {
                UserAppointmentDto userAppointmentDto = new UserAppointmentDto();
                userAppointmentDto.setDate(appointment.getDate());
                userAppointmentDto.setNameType(appointment.getType().getNameType());
                userAppointmentDto.setMechanicEmail(appointment.getMechanic().getEmail());
                userAppointmentDto.setMechanicPhone(appointment.getMechanic().getPhone());
                userAppointmentDto.setMechanicLastName(appointment.getMechanic().getLastName());
                userAppointmentDto.setMehcanicFirstName(appointment.getMechanic().getFirstName());
                userAppointmentDto.setStatus(appointment.getStatus());
                userAppointmentDto.setIdAppointment(appointment.getId());
                userAppointmentDtoList.add(userAppointmentDto);
            }
            return new ResponseEntity<>(userAppointmentDtoList, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getMechanicAppointments(int id){
        List<Appointment> appointmentList = appointmentRepository.findByUser(id);
        if(appointmentList.isEmpty()){
            return new ResponseEntity<>("Mechanic Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
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

}

