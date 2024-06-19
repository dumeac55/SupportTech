package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.MessageDto;
import service.entity.Message;
import service.repository.MessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

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
        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }
}
