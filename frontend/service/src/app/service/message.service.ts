import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageDto } from '../model/message-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  getMessage() : Observable <MessageDto[]>{
    return this.http.get<MessageDto[]>(this.URL + 'message');
  }
}
