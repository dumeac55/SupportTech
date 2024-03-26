package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.Appointment;
import service.entity.MechanicProfile;

@Transactional
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
