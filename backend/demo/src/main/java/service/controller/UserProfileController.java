package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.entity.UserProfile;
import service.services.UserProfileService;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping(value = "/{id}")
    public UserProfile getUserProfileById(@PathVariable("id") int id){
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping(value = "/username={username}")
    public int getUserProfileById(@PathVariable("username") String username){
        return userProfileService.getUserProfileByUsername(username);
    }
}
