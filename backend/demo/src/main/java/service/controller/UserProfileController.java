package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.services.AppointmentService;
import service.services.UserProfileService;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(value = "/{id}")
    private ResponseEntity<?> getUserProfileById(@PathVariable("id") int id){
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping(value = "/username={username}")
    private int getIdUser(@PathVariable("username") String username){
        return userProfileService.getUserProfileByUsername(username);
    }

    @GetMapping(value = "/username={username}/profile")
    private int getIdUserProfile(@PathVariable("username") String username){
        return userProfileService.getIdProfileByUsername(username);
    }

    @GetMapping("/{id}/appointment")
    private ResponseEntity<?> getUserAppointments(@PathVariable("id") int id){
        return appointmentService.getUserAppointments(id);
    }
    @GetMapping(value = "/username={username}/role")
    private String getUserRole(@PathVariable("username") String username){
        return userProfileService.getRoleByUsername(username);
    }
}
