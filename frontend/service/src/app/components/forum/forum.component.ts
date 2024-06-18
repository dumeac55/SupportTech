import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ForumService } from '../../service/forum.service';
import { QuestionForumDto } from '../../model/question-forum-dto';
import { AnswearForumDto } from '../../model/answear-forum-dto';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';

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
  username?: String | null;
  newQuestionTitle: string = '';
  newQuestionDescription: string = '';
  showQuestion: boolean = false;
  showAnswear: boolean = false;
  displayedColumns: string[] = [
    'Col1',
    'Col2',
    'Col3',
  ];

  dataSource = new MatTableDataSource<QuestionForumDto>();
  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  constructor(
    private route: ActivatedRoute,
    private forumService: ForumService
  ) {}

  ngOnInit(): void{
    this.username = localStorage.getItem('username');
    this.route.params.subscribe(async params => {
      const id = params['id'];
      if (id) {
        await this.getQuestionById(id);
        await this.getAnswersByQuestionId(id);
        this.showQuestion = false;
        this.showAnswear =true;
      }
      else{
        await this.getQuestions();
        this.showQuestion = true;
        this.showAnswear = false;
      }
    });
  }

  async getQuestions(): Promise<void>{
    const question = await this.forumService.getQuestions();
    if(Array.isArray(question)){
      this.questions = question;
      for(let i = 0; i<this.questions.length; i++){
        const idQuestion = this.questions[i].idQuestion;
        if (idQuestion !== undefined)
        this.questions[i].totalReplies= await this.forumService.getCountAnswerByQuestion(idQuestion);
      }
      this.dataSource.data = this.questions;
      if (this.paginator) this.dataSource.paginator = this.paginator;
    }
  }

  async getQuestionById(id: number): Promise<void> {
    this.question = await this.forumService.getQuestionById(id);
  }

  async deleteQuestion(id: number | undefined) : Promise<void >{
    if(id){
      const resposne = await this.forumService.deleteQuestionById(id);
      this.questions = this.questions.filter(question => question.idQuestion !== id);
    }
  }

  async addQuestion(): Promise<void> {
    const username = localStorage.getItem('username');
    if (!username) {
      return;
    }

    if (!this.newQuestionDescription.trim() && !this.newQuestionTitle.trim()) {
      return;
    }

    const newQuestion: QuestionForumDto = {
      description: this.newQuestionDescription,
      username: username,
      title: this.newQuestionTitle
    };

    await this.forumService.addQuestion(newQuestion).subscribe(()=>{this.getQuestions()});
    this.newQuestionDescription = '';
    this.newQuestionTitle = '';
  }

  async getAnswersByQuestionId(id: number): Promise<void> {
    const answers = await this.forumService.getAnswearByIdQuestion(id);
    if (Array.isArray(answers)) {
      this.answers = answers;
    }
  }

  async addAnswear(): Promise<void> {
    const username = localStorage.getItem('username');
    if (!username) {
      return;
    }

    const idQuestion = this.question?.idQuestion;
    if (!idQuestion) {
      return;
    }

    if (!this.newAnswerDescription.trim()) {
      return;
    }

    const newAnswer: AnswearForumDto = {
      description: this.newAnswerDescription,
      username: username,
      idQuestion: idQuestion
    };

    await this.forumService.addAnswearByIdQuestion(newAnswer).subscribe(() =>{this.getQuestionById(idQuestion),
                                                                              this.getAnswersByQuestionId(idQuestion) });
    this.newAnswerDescription = '';
  }

  async deleteAnswear(id: number | undefined) : Promise<void >{
    if(id){
      const resposne = await this.forumService.deleteAnswearById(id);
      this.answers = this.answers.filter(answer => answer.idAnswear !== id);
    }
  }
}
