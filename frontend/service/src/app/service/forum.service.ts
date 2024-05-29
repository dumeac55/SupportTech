import { Injectable } from '@angular/core';
import { QuestionForumDto } from '../model/question-forum-dto';
import { HttpClient } from '@angular/common/http';
import { AnswearForumDto } from '../model/answear-forum-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  async getQuestions(): Promise<QuestionForumDto | undefined> {
    return await this.http.get<QuestionForumDto>(this.URL + 'forum').toPromise();
  }

  async getQuestionById(id: number): Promise<QuestionForumDto | undefined> {
    return await this.http.get<QuestionForumDto>(this.URL + 'forum/'+ id).toPromise();
  }
  public addQuestion(question : QuestionForumDto) : Observable <AnswearForumDto>{
    return this.http.post<AnswearForumDto>(this.URL + 'forum/create', question,{ responseType: 'text' as 'json' });
  }

  async deleteQuestionById(id: number): Promise <void | undefined> {
    return await this.http.delete<void>(this.URL+'forum/delete/'+ id,{ responseType: 'text' as 'json' }).toPromise();
  }

  async getAnswearByIdQuestion(id: number): Promise<AnswearForumDto | undefined> {
    return await this.http.get<AnswearForumDto>(this.URL + 'forum/'+ id + "/answear").toPromise();
  }
  addAnswearByIdQuestion(answear :AnswearForumDto) : Observable<AnswearForumDto>{
    return this.http.post<AnswearForumDto>(this.URL + 'forum/create/answear', answear,{ responseType: 'text' as 'json' });
  }

  async deleteAnswearById(id: number): Promise <void | undefined> {
    return await this.http.delete<void>(this.URL+'forum/delete/'+ id + '/answear',{ responseType: 'text' as 'json' }).toPromise();
  }
}
