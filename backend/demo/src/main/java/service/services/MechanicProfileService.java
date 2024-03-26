package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
