package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import service.entity.MechanicProfile;
import service.repository.MechanicProfileRepository;

import java.util.List;

@Service
public class MechanicProfileService {

    @Autowired
    private MechanicProfileRepository mechanicProfileRepository;

    public List<MechanicProfile> getMechanics(){
        return mechanicProfileRepository.findAll();
    }

    public int getMechanicProfileByUsername(String username){
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByUsername(username);
        return mechanicProfile.getIdProfile();
    }
    public ResponseEntity<?> getUserProfileById(int id){
        MechanicProfile mechanicProfile = mechanicProfileRepository.findByIdMechanic(id);
        if (mechanicProfile == null){
            return new ResponseEntity<>("Mechanic not exist", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(mechanicProfile, HttpStatus.OK);
    }
}
