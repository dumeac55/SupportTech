package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.entity.MechanicProfile;
import service.repository.MechanicProfileRepository;
import service.services.AppointmentService;
import service.services.MechanicProfileService;

import java.util.List;

@RestController
@RequestMapping("api/mechanic")
@CrossOrigin(origins = "http://localhost:4200/")
public class MechanicController {
    @Autowired
    private MechanicProfileService mechanicProfileService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    private ResponseEntity<?> getMechanics(){
        return mechanicProfileService.getMechanics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMechanicById(@PathVariable("id") int id){
        return mechanicProfileService.getMechanicProfileByUserId(id);
    }

    @GetMapping("/{id}/appointment")
    private ResponseEntity<?> getUserAppointments(@PathVariable("id") int id){
        return appointmentService.getMechanicAppointments(id);
    }

    @GetMapping(value = "/username={username}")
    public int getUserProfileById(@PathVariable("username") String username){
        return mechanicProfileService.getMechanicProfileByUsername(username);
    }
}
