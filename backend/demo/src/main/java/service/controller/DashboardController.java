package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{year}/appointments")
    private ResponseEntity<?> getNrAppointments(@PathVariable("year") int year) {
        return dashboardService.getNrOfAppointments(year);
    }

    @GetMapping("/{year}/technician")
    private ResponseEntity<?> getNrAppointmentsPerTechnician(@PathVariable("year") int year) {
        return dashboardService.getNrOfAppointmentsPerTechnician(year);
    }

    @GetMapping("/{year}/{id}")
    private ResponseEntity<?> getNrOfAppointmentsPerUser(@PathVariable("year") int year, @PathVariable("id") int idProfile, @RequestParam(name = "status", required = false ) String status) {
        return dashboardService.getNrOfAppointmentsPerUser(year, idProfile, status);
    }
}
