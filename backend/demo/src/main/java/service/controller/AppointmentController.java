package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.AppointmentDto;
import service.dto.updateAppointmentDto;
import service.entity.Appointment;
import service.services.AppointmentService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/appointment")
@CrossOrigin(origins = "http://localhost:4200/")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/all")
    private List<Appointment> getAppointments() {
        return appointmentService.getAppointments();
    }

    @PostMapping("/create")
    private ResponseEntity<?> addAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.addAppointment(appointmentDto);
    }

    @PostMapping("/update")
    private ResponseEntity<?> updateAppointment(@RequestBody updateAppointmentDto upd) {
        int id = upd.getIdAppointment();
        String newStatus = upd.getStatus();
        return appointmentService.updateAppointment(id, newStatus);
    }

    @GetMapping("/orar2")
    private List<String> getMechanicFreeTime(@RequestParam(name = "idMechanic") int id, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return appointmentService.getFreeTimeSlots(id, date);
    }

}
