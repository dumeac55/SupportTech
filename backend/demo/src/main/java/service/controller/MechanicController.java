package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.entity.MechanicProfile;
import service.repository.MechanicProfileRepository;
import service.services.AppointmentService;
import service.services.MechanicProfileService;

import java.util.List;

@RestController
@RequestMapping("api/mechanics")
public class MechanicController {
    @Autowired
    private MechanicProfileService mechanicProfileService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    private List<MechanicProfile> getMechanics(){
        return mechanicProfileService.getMechanics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMechanicById(@PathVariable("id") int id){
        return mechanicProfileService.getUserProfileById(id);
    }

    @GetMapping("/{id}/appointment")
    private ResponseEntity<?> getUserAppointments(@PathVariable("id") int id){
        return appointmentService.getMechanicAppointments(id);
    }
}
