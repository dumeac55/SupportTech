import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ForumService } from '../../service/forum.service';
import { QuestionForumDto } from '../../model/question-forum-dto';
import { AnswearForumDto } from '../../model/answear-forum-dto';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrl: './forum.component.css'
})
export class ForumComponent {
  questions: QuestionForumDto [] = [];
  answers: AnswearForumDto[] = [];
  question: QuestionForumDto | undefined;
  newAnswerDescription: string = '';

  constructor(
    private route: ActivatedRoute,
    private forumService: ForumService
  ) {}

  ngOnInit(): void{
    this.route.params.subscribe(async params => {
      const id = params['id'];
      if (id) {
        await this.getQuestionById(id);
        await this.getAnswersByQuestionId(id);
      }
      await this.getQuestions();
    });
  }

  async getQuestions(): Promise<void>{
    const question = await this.forumService.getQuestions();
    if(Array.isArray(question)){
      this.questions = question;
    }
  }

  async getQuestionById(id: number): Promise<void> {
    this.question = await this.forumService.getQuestionById(id);
  }

  async getAnswersByQuestionId(id: number): Promise<void> {
    const answers = await this.forumService.getAnswearByIdQuestion(id);
    if (Array.isArray(answers)) {
      this.answers = answers;
    }
  }

  async addAnswer(): Promise<void> {
    const username = localStorage.getItem('username');
    if (!username) {
      console.error('Username is not available in localStorage.');
      return;
    }

    const idQuestion = this.question?.idQuestion;
    if (!idQuestion) {
      console.error('Question id is not available.');
      return;
    }

    if (!this.newAnswerDescription.trim()) {
      console.error('Answer description is empty.');
      return;
    }

    const newAnswer: AnswearForumDto = {
      description: this.newAnswerDescription,
      username: username,
      idQuestion: idQuestion
    };

    try {
      console.log(newAnswer);
      await this.forumService.addAnswearByIdQuestion(newAnswer);
      // Reîncarcăm întrebarea și răspunsurile după adăugarea noului răspuns
      await this.getQuestionById(idQuestion);
      await this.getAnswersByQuestionId(idQuestion);
      // Resetăm câmpul de introducere pentru noul răspuns
      this.newAnswerDescription = '';
    } catch (error) {
      console.error('Error adding answer:', error);
    }
  }
}
