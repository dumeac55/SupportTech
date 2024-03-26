package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.Type;

@Transactional
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

}
