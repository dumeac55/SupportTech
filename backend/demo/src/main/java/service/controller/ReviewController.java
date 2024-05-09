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
    private ResponseEntity<?> getReviewsByMechaicId(@RequestParam(name = "idMechanicProfile") int idMechanic){
        return reviewService.getReviewByIdMechanic(idMechanic);
    }
    @GetMapping ("/{id}/grade")
    private ResponseEntity<?> getAvgGradeByIdMechanic(@PathVariable("id") int id){
        return reviewService.getAvgGradeByIdMechanic(id);
    }
    @PostMapping("/create")
    private ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto){
        return reviewService.addReview(reviewDto);
    }
}
