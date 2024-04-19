package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.entity.Appointment;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a JOIN a.user u WHERE a.user.idUser = :userId")
    List<Appointment> findByUser(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a JOIN a.user u WHERE a.mechanic.idMechanic = :userId")
    List<Appointment> findByMechanic(@Param("userId") int userId);

    @Query("SELECT a FROM Appointment a JOIN a.user u WHERE a.mechanic.idMechanic = :userId and a.date = :date")
    List<Appointment> getAppointmentsByDateAndStatus(@Param("date") Date date, @Param("userId") int idMechanic);

}