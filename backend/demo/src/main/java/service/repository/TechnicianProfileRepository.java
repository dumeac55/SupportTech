package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.TechnicianProfile;

@Transactional
@Repository
public interface TechnicianProfileRepository extends JpaRepository<TechnicianProfile, Integer> {
    TechnicianProfile findByUsername(String username);
    TechnicianProfile findByUser_IdUser(int id);
    TechnicianProfile findById(int id);
}