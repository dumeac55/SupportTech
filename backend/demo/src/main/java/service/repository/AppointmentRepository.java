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
    @Query("SELECT a FROM Appointment a JOIN a.userProfile up JOIN up.user u WHERE u.idUser = :userId")
    List<Appointment> findByUserProfile_IdProfile(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a WHERE a.technician.idTechnician = :userId")
    List<Appointment> findByTechnician(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a WHERE a.technician.idTechnician = :userId and date(a.date) = :date")
    List<Appointment> getAppointmentsByDateAndStatus(@Param("date") Date date, @Param("userId") int idTechnician);

    @Query("SELECT new service.dto.DashboardDto(MONTH(a.date) AS month, COUNT(a) AS count) " +
            "FROM Appointment a " +
            "WHERE YEAR(a.date) = :year " +
            "GROUP BY MONTH(a.date)")
    List<DashboardDto> countAppointmentsByMonth(@Param("year") int year);

}