package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        return user.getIdProfile();
    }
    public UserProfile getUserProfileById(int id){
        return userProfileRepository.findByIdProfile(id);
    }

}