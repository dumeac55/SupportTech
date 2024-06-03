package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.TechnicianDto;
import service.entity.TechnicianProfile;
import service.entity.User;
import service.repository.TechnicianProfileRepository;
import service.repository.ReviewRepository;
import service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianProfileService {

    @Autowired
    private TechnicianProfileRepository technicianProfileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    public ResponseEntity<?> getTechnicians(){
        List<TechnicianProfile> technicianProfile = technicianProfileRepository.findAll();
        List<TechnicianDto> technicianDto = new ArrayList<TechnicianDto>();
        if (technicianProfile.isEmpty()){
            return new ResponseEntity<>("Technicians Profile not found", HttpStatus.BAD_REQUEST);
        }
        else{
            for(TechnicianProfile technicianProfile1 : technicianProfile){
                TechnicianDto technicianDto1 = new TechnicianDto();
                technicianDto1.setAvgGrade(reviewRepository.findAvgGradeByTechnicianId(technicianProfile1.getIdTechnician()));
                technicianDto1.setCountGrade(reviewRepository.findCountGradeByTechnicianId(technicianProfile1.getIdTechnician()));
                technicianDto1.setEmail(technicianProfile1.getEmail());
                technicianDto1.setFirstName(technicianProfile1.getFirstName());
                technicianDto1.setLastName(technicianProfile1.getLastName());
                technicianDto1.setPhone(technicianProfile1.getPhone());
                technicianDto1.setUsername(technicianProfile1.getUsername());
                technicianDto.add(technicianDto1);
            }
            return new ResponseEntity<>(technicianDto, HttpStatus.OK);
        }
    }

    public int getTechnicianProfileByUsername(String username){
        TechnicianProfile technicianProfile = technicianProfileRepository.findByUsername(username);
        if(technicianProfile == null){
            return -1;
        }
        return technicianProfile.getIdTechnician();
    }
    public ResponseEntity<?> getTechnicianProfileByUserId(int id){
        TechnicianProfile technicianProfile = technicianProfileRepository.findByUser_IdUser(id);
        if (technicianProfile == null){
            return new ResponseEntity<>("Technician not exist", HttpStatus.BAD_REQUEST);
        }
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setUsername(technicianProfile.getUsername());
        technicianDto.setPhone(technicianProfile.getPhone());
        technicianDto.setEmail(technicianProfile.getEmail());
        technicianDto.setLastName(technicianProfile.getLastName());
        technicianDto.setFirstName(technicianProfile.getFirstName());
        technicianDto.setRole(technicianDto.getRole());
        technicianDto.setRole(getTechnicianRoleByUsername(technicianProfile.getUsername()));
        return  new ResponseEntity<>(technicianDto, HttpStatus.OK);
    }

    public String getTechnicianRoleByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user.getRole();
    }
}