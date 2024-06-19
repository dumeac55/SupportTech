package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
