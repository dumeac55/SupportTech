package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.AnswerForumDto;
import service.dto.QuestionForumDto;
import service.entity.AnswerForum;
import service.entity.QuestionForum;
import service.entity.User;
import service.repository.AnswerForumRepository;
import service.repository.QuestionForumRepository;
import service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    private QuestionForumRepository questionForumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerForumRepository answerForumRepository;

    public ResponseEntity<?> getQuestionForumAll(){
        List<QuestionForum> questionForumList = questionForumRepository.findAll();
        if(questionForumList.isEmpty()){
            return new ResponseEntity<>("No questions", HttpStatus.NO_CONTENT);
        }
        List<QuestionForumDto> questionForumDtos = new ArrayList<>();
        for(QuestionForum questionForum : questionForumList){
            QuestionForumDto questionForumDto = new QuestionForumDto();
            questionForumDto.setIdQuestion(questionForum.getIdQuestion());
            questionForumDto.setUsername(questionForum.getUser().getUsername());
            questionForumDto.setDescription(questionForum.getDescription());
            questionForumDto.setTitle(questionForum.getTitle());

            questionForumDtos.add(questionForumDto);
        }
        return new ResponseEntity<>(questionForumDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> getQuestionForumById(int id){
        QuestionForum questionForum = questionForumRepository.findById(id);
        if(questionForum == null){
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        else{
            QuestionForumDto questionForumDto = new QuestionForumDto();
            questionForumDto.setIdQuestion(questionForum.getIdQuestion());
            questionForumDto.setUsername(questionForum.getUser().getUsername());
            questionForumDto.setDescription(questionForum.getDescription());
            questionForumDto.setTitle(questionForum.getTitle());
            return new ResponseEntity<>(questionForumDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addNewQuestion(QuestionForumDto questionForumDto){
        if(questionForumDto == null){
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
        QuestionForum newQuestionForum = new QuestionForum();
        User user = userRepository.findByUsername(questionForumDto.getUsername());
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        newQuestionForum.setTitle(questionForumDto.getTitle());
        newQuestionForum.setDescription(questionForumDto.getDescription());
        newQuestionForum.setUser(user);
        questionForumRepository.save(newQuestionForum);
        return new ResponseEntity<>("Question is added successfull", HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteQuestion(int id){
        QuestionForum questionForum = questionForumRepository.findById(id);
        if(questionForum == null){
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        answerForumRepository.deleteByQuestionForum_idQuestion(id);
        questionForumRepository.deleteById(id);
        return new ResponseEntity<>("Question deleted successfull", HttpStatus.OK);
    }

    public ResponseEntity<?> getAnswerByQuestionId(int idQuestion){
        QuestionForum questionForum = questionForumRepository.findById(idQuestion);
        if( questionForum == null){
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        List<AnswerForum> answerForums = answerForumRepository.findByQuestionForum_idQuestion(idQuestion);
        List<AnswerForumDto> answarForumDtos = new ArrayList<>();
        for(AnswerForum answerForum : answerForums){
            AnswerForumDto answerForumDto = new AnswerForumDto();
            answerForumDto.setDescription(answerForum.getDescription());
            answerForumDto.setIdQuestion(answerForum.getQuestionForum().getIdQuestion());
            answerForumDto.setUsername(answerForum.getUser().getUsername());
            answerForumDto.setIdAnswer(answerForum.getIdAnswer());
            answarForumDtos.add(answerForumDto);
        }
        return new ResponseEntity<>(answarForumDtos, HttpStatus.OK);

    }

    public ResponseEntity<?> addAnswerByQuestionId(AnswerForumDto answerForumDto){
        User user = userRepository.findByUsername(answerForumDto.getUsername());
        QuestionForum questionForum = questionForumRepository.findById(answerForumDto.getIdQuestion());
        if( user == null || questionForum == null ){
            return new ResponseEntity<>("user/ question not found", HttpStatus.NOT_FOUND);
        }

        AnswerForum answerForum = new AnswerForum();
        answerForum.setQuestionForum(questionForum);
        answerForum.setUser(user);
        answerForum.setDescription(answerForumDto.getDescription());
        answerForumRepository.save(answerForum);
        return new ResponseEntity<>("Answer successfull added", HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteAnswer(int id){
        AnswerForum answerForum = answerForumRepository.findByIdAnswer(id);
        if(answerForum == null){
            return new ResponseEntity<>("Answer not found", HttpStatus.NOT_FOUND);
        }
        answerForumRepository.deleteById(id);
        return new ResponseEntity<>("Answer successfull delete", HttpStatus.OK);
    }

    public ResponseEntity<?> getCountAnswersById(int id){
        Long result = answerForumRepository.findAllWithCountAnswer(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
