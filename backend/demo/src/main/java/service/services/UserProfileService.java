package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.entity.User;
import service.entity.UserProfile;
import service.exception.UserException;
import service.exception.UserProfileException;
import service.repository.UserProfileRepository;
import service.repository.UserRepository;

import java.nio.file.attribute.UserPrincipal;
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

}