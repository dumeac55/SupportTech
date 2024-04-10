package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.AppointmentDto;
import service.entity.Appointment;
import service.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/all")
    private List<Appointment> getAppointments(){
        return appointmentService.getAppointments();
    }
    @PostMapping("/create")
    private ResponseEntity<?> addAppointment(@RequestBody AppointmentDto appointmentDto){
        return appointmentService.addAppointment(appointmentDto);
    }


}
