package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.dto.DashboardDto;
import service.dto.ReviewDto;
import service.dto.TechnicianDto;
import service.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByTechnicianProfile_IdTechnician(int idTechnician);

    @Query("SELECT AVG(r.grade) FROM Review r WHERE r.technicianProfile.idTechnician = :TechnicianId")
    Float findAvgGradeByTechnicianId(int TechnicianId);

    @Query("SELECT AVG(r.grade), t.firstName, t.lastName FROM Review r JOIN r.technicianProfile t GROUP BY t.idTechnician ORDER BY AVG(r.grade)")
    List<String> findAvgGradeTechnicians();

    @Query("SELECT COUNT(r.grade) FROM Review r WHERE r.technicianProfile.idTechnician = :TechnicianId")
    Float findCountGradeByTechnicianId(int TechnicianId);
}