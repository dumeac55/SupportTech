import { Component } from '@angular/core';
import { ChatDto } from '../../model/chat-dto';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent{
  isChatOpen = false;
  messages: ChatDto[] = [];
  username: any = localStorage.getItem('username');
  newMessage: ChatDto = { username: this.username, content: '' };

  constructor(private chatService: ChatService) { }

  ngOnInit() {
    this.loadMessages();
  }

  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
    if (this.isChatOpen) {
      this.loadMessages();
    }
  }

  closeChat() {
    this.isChatOpen = false;
  }

  loadMessages() {
    this.chatService.getMessage().subscribe(
      (response) => {
        this.messages = response;
      }
    );
  }

  sendMessage() {
    if (this.newMessage && this.newMessage.content && this.newMessage.content.trim() !== '') {
      this.chatService.addMessage(this.newMessage).subscribe(
        (response) => {
          this.newMessage.content = '';
          this.loadMessages();
        },
        (error) =>{
          if(error.status === 201){
            this.newMessage.content = '';
            this.loadMessages();
          }
        }
      );
    }
  }
}
