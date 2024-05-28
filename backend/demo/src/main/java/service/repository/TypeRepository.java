package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.Type;

import java.util.List;

@Transactional
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findByNameTypeAndTechnicianProfile_Username(String name, String technicianUsername);
    Type findById(int id);
    List<Type> findByTechnicianProfile_IdTechnician(int idTechnician);
}
