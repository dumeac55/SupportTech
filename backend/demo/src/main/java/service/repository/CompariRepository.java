package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.Appointment;
import service.entity.Compari;

@Repository
public interface CompariRepository extends JpaRepository<Compari, Integer> {
}
