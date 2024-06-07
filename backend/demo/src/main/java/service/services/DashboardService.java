package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.ReviewDto;
import service.dto.TechnicianDto;
import service.entity.TechnicianProfile;
import service.repository.ReviewRepository;
import service.repository.TechnicianProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ResponseEntity<?> getTechniciansAvg(){
        List<String> technicianDtoList = reviewRepository.findAvgGradeTechnicians();
        List<String> finalList= new ArrayList<>();
        for(String technicianDto : technicianDtoList){

            finalList.add(technicianDto);
        }
        return new ResponseEntity<>(finalList, HttpStatus.OK);
    }
}
