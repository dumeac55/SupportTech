package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMechanicProfile_IdMechanic(int idMechanic);

    @Query("SELECT AVG(r.grade) FROM Review r WHERE r.mechanicProfile.idMechanic = :mechanicId")
    Float findAvgGradeByMechanicId(int mechanicId);
}
