package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.UserProfileDto;
import service.entity.TechnicianProfile;
import service.entity.User;
import service.entity.UserProfile;
import service.repository.TechnicianProfileRepository;
import service.repository.UserProfileRepository;
import service.repository.UserRepository;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TechnicianProfileRepository technicianProfileRepository;

    public List<UserProfile> getAllUser(){
        return userProfileRepository.findAll();
    }
    public Boolean existByPhone(String phone) {
        UserProfile userProfile = userProfileRepository.findByPhone(phone);
        if (userProfile != null) {
            return true;
        }
        return false;
    }

    public void addUserProfile(UserProfile userProfile){
        userProfileRepository.save(userProfile);
    }
    public int getUserProfileByUsername(String username){
        if(getRoleByUsername(username).equals("custom")) {
            UserProfile user = userProfileRepository.findByUsername(username);
            return user.getUser().getIdUser();
        }
        else{
            TechnicianProfile technician = technicianProfileRepository.findByUsername(username);
            return technician.getUser().getIdUser();
        }
    }

    public ResponseEntity<?> getUserProfileById(int id){
        UserProfileDto userProfileDto = new UserProfileDto();
        UserProfile userProfile = userProfileRepository.findByUser_IdUser(id);
        if(userProfile == null){
            return new ResponseEntity<>("User Profile Not Found", HttpStatus.BAD_REQUEST);
        }
        else{
            userProfileDto.setEmail(userProfile.getEmail());
            userProfileDto.setFirstName(userProfile.getFirstName());
            userProfileDto.setPhone(userProfile.getPhone());
            userProfileDto.setLastName(userProfile.getLastName());
            userProfileDto.setUsername(userProfile.getUsername());
            userProfileDto.setRole(getRoleByUsername(userProfile.getUsername()));
            return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
        }
    }

    public String getRoleByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user.getRole();
    }

}