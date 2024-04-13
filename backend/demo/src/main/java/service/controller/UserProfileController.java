package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.entity.UserProfile;
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
    public ResponseEntity<?> getUserProfileById(@PathVariable("id") int id){
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping(value = "/username={username}")
    public int getUserProfileById(@PathVariable("username") String username){
        return userProfileService.getUserProfileByUsername(username);
    }

    @GetMapping("/{id}/appointment")
    private ResponseEntity<?> getUserAppointments(@PathVariable("id") int id){
        return appointmentService.getUserAppointments(id);
    }
    @GetMapping(value = "/username={username}/role")
    public String getUserRole(@PathVariable("username") String username){
        return userProfileService.getRoleByUsername(username);
    }
}
