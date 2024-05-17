package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.ReviewDto;
import service.entity.TechnicianProfile;
import service.entity.Review;
import service.entity.UserProfile;
import service.repository.TechnicianProfileRepository;
import service.repository.ReviewRepository;
import service.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private TechnicianProfileRepository technicianProfileRepository;
    public ResponseEntity<?> getReviewByIdTechnician(int idTechnician){

        TechnicianProfile technicianProfile1 = technicianProfileRepository.findById(idTechnician);
        if( technicianProfile1 == null){
            return new ResponseEntity<>("Technician not found", HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.findByTechnicianProfile_IdTechnician(technicianProfile1.getIdTechnician());
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review :reviews){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setTechnicianId(review.getTechnicianProfile().getIdTechnician());
            reviewDto.setDescription(review.getDescription());
            reviewDto.setGrade(review.getGrade());
            reviewDto.setUserId(review.getUserProfile().getIdProfile());
            reviewDto.setAvgGrade(reviewRepository.findAvgGradeByTechnicianId(idTechnician));
            reviewDtos.add(reviewDto);
        }
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> getAvgGradeByIdTechnician(int id){
        return new ResponseEntity<>(reviewRepository.findAvgGradeByTechnicianId(id).toString(), HttpStatus.OK);
    }

    public ResponseEntity<?> addReview(ReviewDto reviewDto){
        UserProfile userProfile = userProfileRepository.findByIdProfile(reviewDto.getUserId());
        TechnicianProfile technicianProfile = technicianProfileRepository.findById(reviewDto.getTechnicianId());
        Review review = new Review();
        review.setDescription(reviewDto.getDescription());
        review.setGrade(reviewDto.getGrade());
        review.setTechnicianProfile(technicianProfile);
        review.setUserProfile(userProfile);
        reviewRepository.save(review);
        return new ResponseEntity<>("Review successfull added", HttpStatus.OK);

    }
}
