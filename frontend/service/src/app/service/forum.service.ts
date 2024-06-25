import { Injectable } from '@angular/core';
import { QuestionForumDto } from '../model/question-forum-dto';
import { HttpClient } from '@angular/common/http';
import { AnswerForumDto } from '../model/answer-forum-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ForumService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  async getQuestions(): Promise<QuestionForumDto | undefined> {
    return await this.http
      .get<QuestionForumDto>(this.URL + 'forum')
      .toPromise();
  }

  async getQuestionById(id: number): Promise<QuestionForumDto | undefined> {
    return await this.http
      .get<QuestionForumDto>(this.URL + 'forum/' + id)
      .toPromise();
  }

  addQuestion(question: QuestionForumDto): Observable<AnswerForumDto> {
    return this.http.post<AnswerForumDto>(
      this.URL + 'forum/create',
      question,
      { responseType: 'text' as 'json' }
    );
  }

  async deleteQuestionById(id: number): Promise<void | undefined> {
    return await this.http
      .delete<void>(this.URL + 'forum/delete/' + id, {
        responseType: 'text' as 'json',
      })
      .toPromise();
  }

  async getAnswerByIdQuestion(
    id: number
  ): Promise<AnswerForumDto | undefined> {
    return await this.http
      .get<AnswerForumDto>(this.URL + 'forum/' + id + '/answear')
      .toPromise();
  }

  addAnswerByIdQuestion(
    answear: AnswerForumDto
  ): Observable<AnswerForumDto> {
    return this.http.post<AnswerForumDto>(
      this.URL + 'forum/create/answear',
      answear,
      { responseType: 'text' as 'json' }
    );
  }

  async deleteAnswerById(id: number): Promise<void | undefined> {
    return await this.http
      .delete<void>(this.URL + 'forum/delete/' + id + '/answear', {
        responseType: 'text' as 'json',
      })
      .toPromise();
  }

  async getCountAnswerByQuestion(id: number): Promise<number | undefined> {
    return this.http
      .get<number>(this.URL + 'forum/' + id + '/question-answer')
      .toPromise();
  }
}
