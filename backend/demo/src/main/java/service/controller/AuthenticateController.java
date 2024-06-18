package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.dto.EmailDto;
import service.dto.LogInDto;
import service.dto.SignUpDto;
import service.entity.User;
import service.entity.UserProfile;
import service.jwt.JwtUtil;
import service.services.CustomUserDetailsService;
import service.services.EmailService;
import service.services.UserProfileService;
import service.services.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/authenticate")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthenticateController {
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authentificateUser(@RequestBody LogInDto logInDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDto.getUsername(), logInDto.getPassword(),
                            customUserDetailsService.loadUserByUsername(logInDto.getUsername()).getAuthorities()
                    ));
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);
        }

        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(logInDto.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        if (userService.existByUsername(signUpDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        if (userService.existByEmail(signUpDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        if (userProfileService.existByPhone(signUpDto.getPhone())) {
            return ResponseEntity.badRequest().body("Phone already exists!");
        } else {
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
            newProfile.setUsername(signUpDto.getUsername());
            EmailDto emailDto = new EmailDto();
            emailDto.setSubject("Welcome");
            emailDto.setMsgBody("Hi," + signUpDto.getUsername() + ",\n" + "\n" + "Your account was created successfully.");
            emailDto.setRecipient(signUpDto.getEmail());
            new Thread(() -> {
                emailService.sendSimpleMail(emailDto);
            }).start();
            userProfileService.addUserProfile(newProfile);

            return ResponseEntity.ok("User regisltered successfully");
        }
    }


}