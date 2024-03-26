package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.entity.MechanicProfile;
import service.repository.MechanicProfileRepository;
import service.services.MechanicProfileService;

import java.util.List;

@RestController
@RequestMapping("api/mechanics")
public class MechanicController {
    @Autowired
    private MechanicProfileService mechanicProfileService;

    @GetMapping
    private List<MechanicProfile> getMechanics(){
        return mechanicProfileService.getMechanics();
    }
}
