package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.dto.LogInDto;
import service.dto.SignUpDto;
import service.entity.User;
import service.entity.UserProfile;
import service.jwt.JwtUtil;
import service.services.CustomUserDetailsService;
import service.services.UserProfileService;
import service.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/authenticate")
@CrossOrigin("http://localhost:4200/")
public class UserController {
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signin")
    public ResponseEntity<String>  authentificateUser(@RequestBody LogInDto logInDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDto.getUsername(), logInDto.getPassword(),
                        customUserDetailsService.loadUserByUsername(logInDto.getUsername()).getAuthorities()
                ));
        if( authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(logInDto.getUsername());
            String token= jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>("token generat"+ token, HttpStatus.OK);
        }
        return new ResponseEntity<>("eroare",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user-profile")
    public List<UserProfile> getAllUserProfiless() {
        return userProfileService.getAllUser();
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
        if(userService.existByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username already exist!", HttpStatus.BAD_REQUEST);
        }
        if(userService.existByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exist!", HttpStatus.BAD_REQUEST);
        }
        if(userProfileService.existByPhone(signUpDto.getPhone())){
            return new ResponseEntity<>("Phone already exist!", HttpStatus.BAD_REQUEST);
        }
        else{
            String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
            User newUser = new User();
            newUser.setUsername(signUpDto.getUsername());
            newUser.setEmail(signUpDto.getEmail());
            newUser.setPassword(encodedPassword);
            newUser.setRole("custom");
            userService.addUser(newUser);

            UserProfile newProfile = new UserProfile();
            newProfile.setUser(newUser);
            newProfile.setFirstName(signUpDto.getFirstName());
            newProfile.setLastName(signUpDto.getLastName());
            newProfile.setPhone(signUpDto.getPhone());
            newProfile.setEmail(signUpDto.getEmail());
            newProfile.setPoints(100);

            userProfileService.addUserProfile(newProfile);

            return ResponseEntity.status(HttpStatus.CREATED).body("The user has been successfully registered!");
        }
    }

}
