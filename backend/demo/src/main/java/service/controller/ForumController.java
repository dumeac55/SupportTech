package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.AnswerForumDto;
import service.dto.QuestionForumDto;
import service.services.ForumService;

@RestController
@RequestMapping("api/forum")
@CrossOrigin(origins = "http://localhost:4200/")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping
    private ResponseEntity<?> getQuestions(){
        return forumService.getQuestionForumAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getQuestionById(@PathVariable("id") int id){
        return forumService.getQuestionForumById(id);
    }
    @PostMapping("/create")
    private ResponseEntity<?> addQuestion(@RequestBody QuestionForumDto questionForumDto){
        return forumService.addNewQuestion(questionForumDto);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteQuestion(@PathVariable("id") int id){
        return forumService.deleteQuestion(id);
    }

    @GetMapping("/{id}/answear")
    private ResponseEntity getAnswearsByIdQuestion(@PathVariable("id") int idQuestion){
        return forumService.getAnswerByQuestionId(idQuestion);
    }

    @PostMapping("/create/answear")
    private ResponseEntity addAnswearAtQuestion(@RequestBody AnswerForumDto answerForumDto){
        return forumService.addAnswerByQuestionId(answerForumDto);
    }

    @DeleteMapping("/delete/{id}/answear")
    private ResponseEntity<?> deleteAnswear(@PathVariable("id") int id){
        return forumService.deleteAnswer(id);
    }

    @GetMapping("/{id}/question-answer")
    private ResponseEntity<?> getCountAnswerByQuestion(@PathVariable("id") int idQuestion){
        return forumService.getCountAnswersById(idQuestion);
    }
}
