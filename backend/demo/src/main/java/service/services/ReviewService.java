package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.ReviewDto;
import service.entity.MechanicProfile;
import service.entity.Review;
import service.entity.UserProfile;
import service.repository.MechanicProfileRepository;
import service.repository.ReviewRepository;
import service.repository.UserProfileRepository;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private MechanicProfileRepository mechanicProfileRepository;
    public ResponseEntity<?> getReviewByIdMechanic(int idMechanic){

        MechanicProfile mechanicProfile1 = mechanicProfileRepository.findById(idMechanic);
        if( mechanicProfile1 == null){
            return new ResponseEntity<>("Mechanic not found", HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.findByMechanicProfile_IdMechanic(mechanicProfile1.getIdProfile());
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review :reviews){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setMechanicId(review.getMechanicProfile().getIdProfile());
            reviewDto.setDescription(review.getDescription());
            reviewDto.setGrade(review.getGrade());
            reviewDto.setUserId(review.getUserProfile().getIdProfile());
            reviewDto.setAvgGrade(reviewRepository.findAvgGradeByMechanicId(idMechanic));
            reviewDtos.add(reviewDto);
        }
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> getAvgGradeByIdMechanic(int id){
        return new ResponseEntity<>(reviewRepository.findAvgGradeByMechanicId(id).toString(), HttpStatus.OK);
    }
}
