package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.MessageDto;
import service.entity.Message;
import service.entity.User;
import service.repository.MessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import service.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getMessage(){
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        List<Message> messagePage = messageRepository.findAll(pageRequest).getContent();
        List<MessageDto> messageDTOs = new ArrayList<>();
        for(Message m : messagePage){
            MessageDto messageDto= new MessageDto();
            messageDto.setContent(m.getContent());
            messageDto.setIdMessage(m.getId());
            messageDto.setUsername(m.getUser().getUsername());
            messageDTOs.add(messageDto);
        }
        Collections.reverse(messageDTOs);
        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }

    public ResponseEntity<?> addMessage(MessageDto messageDto){
        if(messageDto != null) {
            Message message = new Message();
            User user = userRepository.findByUsername(messageDto.getUsername());
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            message.setContent(messageDto.getContent());
            message.setUser(user);
            messageRepository.save(message);
            return new ResponseEntity<>("Message success added", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Message not added", HttpStatus.BAD_REQUEST);
        }
    }
}
