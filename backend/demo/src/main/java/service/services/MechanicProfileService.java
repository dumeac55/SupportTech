package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import service.dto.MechanicDto;
import service.entity.MechanicProfile;
import service.entity.User;
import service.repository.MechanicProfileRepository;
import service.repository.ReviewRepository;
import service.repository.UserRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
public class MechanicProfileService {

    @Autowired
    private MechanicProfileRepository mechanicProfileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    public ResponseEntity<?> getMechanics(){
        List<MechanicProfile> mechanicProfile = mechanicProfileRepository.findAll();
        List<MechanicDto> mechanicDto = new ArrayList<MechanicDto>();
        if (mechanicProfile.isEmpty()){
            return new ResponseEntity<>("Mechanics Profile not found", HttpStatus.BAD_REQUEST);
        }
        else{
            for(MechanicProfile mechanicProfile1: mechanicProfile){
                MechanicDto mechanicDto1 = new MechanicDto();
                mechanicDto1.setAvgGrade(reviewRepository.findAvgGradeByMechanicId(mechanicProfile1.getIdProfile()));
                mechanicDto1.setEmail(mechanicProfile1.getEmail());
                mechanicDto1.setFirstName(mechanicProfile1.getFirstName());
                mechanicDto1.setLastName(mechanicProfile1.getLastName());
                mechanicDto1.setPhone(mechanicProfile1.getPhone());
                mechanicDto1.setUsername(mechanicProfile1.getUsername());
                mechanicDto.add(mechanicDto1);
            }
            return new ResponseEntity<>(mechanicDto, HttpStatus.OK);
        }
    }

    public int getMechanicProfileByUsername(String username){
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByUsername(username);
        if(mechanicProfile == null){
            return -1;
        }
        return mechanicProfile.getIdProfile();
    }
    public ResponseEntity<?> getMechanicProfileByUserId(int id){
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByUser_IdUser(id);
        if (mechanicProfile == null){
            return new ResponseEntity<>("Mechanic not exist", HttpStatus.BAD_REQUEST);
        }
        MechanicDto mechanicDto = new MechanicDto();
        mechanicDto.setUsername(mechanicProfile.getUsername());
        mechanicDto.setPhone(mechanicProfile.getPhone());
        mechanicDto.setEmail(mechanicProfile.getEmail());
        mechanicDto.setLastName(mechanicProfile.getLastName());
        mechanicDto.setFirstName(mechanicProfile.getFirstName());
        mechanicDto.setRole(mechanicDto.getRole());
        mechanicDto.setRole(getMechanicRoleByUsername(mechanicProfile.getUsername()));
        return  new ResponseEntity<>(mechanicDto, HttpStatus.OK);
    }

    public String getMechanicRoleByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user.getRole();
    }
}
