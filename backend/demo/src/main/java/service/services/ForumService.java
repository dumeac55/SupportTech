package service.services;

import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.AnswearForumDto;
import service.dto.QuestionForumDto;
import service.entity.AnswearForum;
import service.entity.QuestionForum;
import service.entity.User;
import service.repository.AnswearForumRepository;
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
    private AnswearForumRepository answearForumRepository;

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
        answearForumRepository.deleteByQuestionForum_idQuestion(id);
        questionForumRepository.deleteById(id);
        return new ResponseEntity<>("Question deleted successfull", HttpStatus.OK);
    }

    public ResponseEntity<?> getAnswearByQuestionId(int idQuestion){
        QuestionForum questionForum = questionForumRepository.findById(idQuestion);
        if( questionForum == null){
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        List<AnswearForum> answearForums = answearForumRepository.findByQuestionForum_idQuestion(idQuestion);
        List<AnswearForumDto> answearForumDtos = new ArrayList<>();
        for(AnswearForum answearForum : answearForums){
            AnswearForumDto answearForumDto = new AnswearForumDto();
            answearForumDto.setDescription(answearForum.getDescription());
            answearForumDto.setIdQuestion(answearForum.getQuestionForum().getIdQuestion());
            answearForumDto.setUsername(answearForum.getUser().getUsername());
            answearForumDto.setIdAnswear(answearForum.getIdAnswear());
            answearForumDtos.add(answearForumDto);
        }
        return new ResponseEntity<>(answearForumDtos, HttpStatus.OK);

    }

    public ResponseEntity<?> addAnswearByQuestionId(AnswearForumDto answearForumDto){
        User user = userRepository.findByUsername(answearForumDto.getUsername());
        QuestionForum questionForum = questionForumRepository.findById(answearForumDto.getIdQuestion());
        if( user == null || questionForum == null ){
            return new ResponseEntity<>("user/ question not found", HttpStatus.NOT_FOUND);
        }

        AnswearForum answearForum = new AnswearForum();
        answearForum.setQuestionForum(questionForum);
        answearForum.setUser(user);
        answearForum.setDescription(answearForumDto.getDescription());
        answearForumRepository.save(answearForum);
        return new ResponseEntity<>("Answear successfull added", HttpStatus.CREATED);

    }
}
