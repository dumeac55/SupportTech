import { Component } from '@angular/core';
import { ChatDto } from '../../model/chat-dto';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {
  isChatOpen = false;
  messages: ChatDto[] = [];
  newMessageText: string = '';
  username: any = localStorage.getItem('username');
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
}
