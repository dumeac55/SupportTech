package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.Message;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
