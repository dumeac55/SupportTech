import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChatDto } from '../model/chat-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  getMessage() : Observable <ChatDto[]>{
    return this.http.get<ChatDto[]>(this.URL + 'message');
  }

  addMessage(message: ChatDto): Observable<any>{
    return this.http.post(this.URL + 'message/create', message, {responseType: 'json' as 'text'});
  }
}
