package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.DashboardDto;
import service.repository.AppointmentRepository;
import service.repository.ReviewRepository;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public ResponseEntity<?> getTechniciansAvg() {
        List<DashboardDto> technicianDtoList = reviewRepository.findAvgGradeTechnicians();
        return new ResponseEntity<>(technicianDtoList, HttpStatus.OK);
    }

    public ResponseEntity<?> getNrOfAppointments(int year){
        List<DashboardDto> nrAppointemntsList = appointmentRepository.countAppointmentsByMonth(year);
        return new ResponseEntity<>(nrAppointemntsList, HttpStatus.OK);
    }

    public ResponseEntity<?> getNrOfAppointmentsPerTechnician(int year){
        List<DashboardDto> nrAppointemntsList = appointmentRepository.countAppointmentsByTechnicianAndYear(year);
        return new ResponseEntity<>(nrAppointemntsList, HttpStatus.OK);
    }

    public ResponseEntity<?> getNrOfAppointmentsPerUser(int year, int idProfile, String status) {
        List<DashboardDto> result;
        if (status != null && (status.equals("Done") || status.equals("Pending"))) {
            result = appointmentRepository.countAppointmentsByUserProfile(year, idProfile, status);
        } else {
            result = appointmentRepository.countAppointmentsByUserProfile(year, idProfile, null);
        }
        return ResponseEntity.ok(result);
    }
}
