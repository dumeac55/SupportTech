package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.UserProfileDto;
import service.entity.UserProfile;
import service.repository.UserProfileRepository;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
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
        UserProfile user = userProfileRepository.findByUsername(username);

        return user.getUser().getIdUser();
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
            userProfileDto.setPoints(userProfile.getPoints());
            userProfileDto.setLastName(userProfile.getLastName());
            userProfileDto.setUsername(userProfile.getUsername());
            return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
        }
    }

}