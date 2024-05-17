package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.services.CompariService;

@RestController
@RequestMapping("api/compari")
@CrossOrigin(origins = "http://localhost:4200/")
public class CompariController {
    @Autowired
    private CompariService compariService;

    @GetMapping("/evomag")
    private ResponseEntity<?> testComparievomag(){
        return compariService.testCompariEvomag();
    }
    @GetMapping("/cel")
    private ResponseEntity<?> testComparicel(){
        return compariService.testCompariCel();
    }
    @GetMapping("/emag")
    private ResponseEntity<?> testCompariemag(){
        return compariService.testCompariEmag();
    }
}
