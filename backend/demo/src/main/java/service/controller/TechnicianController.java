package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.services.AppointmentService;
import service.services.TechnicianProfileService;

@RestController
@RequestMapping("api/technician")
@CrossOrigin(origins = "http://localhost:4200/")
public class TechnicianController {
    @Autowired
    private TechnicianProfileService technicianProfileService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    private ResponseEntity<?> getTechnicians(){
        return technicianProfileService.getTechnicians();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTechnicianById(@PathVariable("id") int id){
        return technicianProfileService.getTechnicianProfileByUserId(id);
    }

    @GetMapping("/{id}/appointment")
    private ResponseEntity<?> getUserAppointments(@PathVariable("id") int id){
        return appointmentService.getTechnicianAppointments(id);
    }

    @GetMapping(value = "/username={username}")
    public int getUserProfileById(@PathVariable("username") String username){
        return technicianProfileService.getTechnicianProfileByUsername(username);
    }
}