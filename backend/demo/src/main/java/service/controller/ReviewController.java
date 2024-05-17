package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.ReviewDto;
import service.dto.TypeDto;
import service.services.ReviewService;

@RestController
@RequestMapping("api/review")
@CrossOrigin(origins = "http://localhost:4200/")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    private ResponseEntity<?> getReviewsByTechnicianId(@RequestParam(name = "idTechnicianProfile") int idTechnician){
        return reviewService.getReviewByIdTechnician(idTechnician);
    }
    @GetMapping ("/{id}/grade")
    private ResponseEntity<?> getAvgGradeByIdTechnician(@PathVariable("id") int id){
        return reviewService.getAvgGradeByIdTechnician(id);
    }
    @PostMapping("/create")
    private ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto){
        return reviewService.addReview(reviewDto);
    }
}
