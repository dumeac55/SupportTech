package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.MessageDto;
import service.services.MessageService;

@RestController
@RequestMapping("api/message")
@CrossOrigin(origins = "http://localhost:4200/")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    private ResponseEntity<?> getMessage(){
        return this.messageService.getMessage();
    }

    @PostMapping("/create")
    private ResponseEntity<?> addMesage(@RequestBody MessageDto messageDto){
        return this.messageService.addMessage(messageDto);
    }
}
