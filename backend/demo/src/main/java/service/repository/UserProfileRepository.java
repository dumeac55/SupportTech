package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.UserProfile;

@Transactional
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByPhone(String phone);
    UserProfile findByUsername(String username);
    UserProfile findByUser_IdUser(int userId);
    UserProfile findByIdProfile(int id);
}