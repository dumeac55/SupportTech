package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
