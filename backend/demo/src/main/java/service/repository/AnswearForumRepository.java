package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.AnswearForum;

import java.util.List;

@Repository
@Transactional
public interface AnswearForumRepository extends JpaRepository<AnswearForum, Integer> {
    List<AnswearForum> findByQuestionForum_idQuestion(int id);
    void deleteByQuestionForum_idQuestion(int id);
}