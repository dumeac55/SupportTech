package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.dto.DashboardDto;
import service.entity.Appointment;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a JOIN a.userProfile up JOIN up.user u WHERE u.idUser = :userId order by a.date desc")
    List<Appointment> findByUserProfile_IdProfile(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a WHERE a.technician.idTechnician = :userId order by a.date desc")
    List<Appointment> findByTechnician(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a WHERE a.technician.idTechnician = :userId and date(a.date) = :date")
    List<Appointment> getAppointmentsByDateAndStatus(@Param("date") Date date, @Param("userId") int idTechnician);

    @Query("SELECT new service.dto.DashboardDto(MONTH(a.date) AS month, COUNT(a) AS count) " +
            "FROM Appointment a " +
            "WHERE YEAR(a.date) = :year " +
            "GROUP BY MONTH(a.date)")
    List<DashboardDto> countAppointmentsByMonth(@Param("year") int year);

    @Query("SELECT new service.dto.DashboardDto(t.firstName AS firstName, t.lastName AS lastName, COUNT(a) AS count) " +
            "FROM Appointment a " +
            "JOIN a.technician t " +
            "WHERE YEAR(a.date) = :year " +
            "GROUP BY t.firstName, t.lastName")
    List<DashboardDto> countAppointmentsByTechnicianAndYear(@Param("year") int year);

    @Query("SELECT new service.dto.DashboardDto(MONTH(a.date) AS month, COUNT(a) AS count) " +
            "FROM Appointment a " +
            "WHERE YEAR(a.date) = :year AND a.userProfile.user.idUser = :idProfile " +
            "AND (:status IS NULL OR a.status = :status) " +
            "GROUP BY MONTH(a.date)")
    List<DashboardDto> countAppointmentsByUserProfile(@Param("year") int year, @Param("idProfile") int idProfile, @Param("status") String status);


    @Query("SELECT new service.dto.DashboardDto(MONTH(a.date) AS month, SUM(a.price) AS price, COUNT(a) as count) " +
            "FROM Appointment a " +
            "WHERE YEAR(a.date) = :year AND a.userProfile.user.idUser = :idProfile " +
            "AND status = 'Done' " +
            "GROUP BY MONTH(a.date)")
    List<DashboardDto> countMoneyPerUser(@Param("year") int year, @Param("idProfile") int idProfile);

    @Query("SELECT new service.dto.DashboardDto(a.status, COUNT(a)) " +
            "FROM Appointment a " +
            "WHERE YEAR(a.date) = :year " +
            "GROUP BY a.status")
    List<DashboardDto> countAppointmentsByStatus(@Param("year") int year);
}