package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.QuestionForum;

import java.util.List;
@Repository
public interface QuestionForumRepository extends JpaRepository<QuestionForum, Integer> {
    QuestionForum findById(int id);
}