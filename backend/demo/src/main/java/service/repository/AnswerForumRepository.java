package service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.entity.AnswerForum;

import java.util.List;

@Repository
@Transactional
public interface AnswerForumRepository extends JpaRepository<AnswerForum, Integer> {
    List<AnswerForum> findByQuestionForum_idQuestion(int id);
    void deleteByQuestionForum_idQuestion(int id);
    AnswerForum findByIdAnswer(int id);

    @Query("SELECT Count(a) FROM AnswerForum a WHERE a.questionForum.idQuestion = :id")
    Long findAllWithCountAnswer(int id);
}