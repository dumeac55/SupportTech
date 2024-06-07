package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.dto.TechnicianDto;
import service.entity.Appointment;
import service.services.DashboardService;

import java.util.List;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin(origins = "http://localhost:4200/")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/all")
    private ResponseEntity<?> getAvgTechnicians() {
        return dashboardService.getTechniciansAvg();
    }
}
