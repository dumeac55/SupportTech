import { Component } from '@angular/core';
import { SignUpDto } from '../../model/SignUpDto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  constructor(private http: HttpClient,
              private router:Router,
              private notification: NotificationService
            ) {}
  succes: Number = 0;
  signUnDto: SignUpDto = {};
  errorMessage: string = '';

  signUp(): void {
    this.errorMessage = '';
    this.http.post<any>('http://localhost:8080/api/authenticate/signup', this.signUnDto, { responseType: 'text' as 'json' })
      .subscribe(
        response => {
          console.log(response);
          this.notification.showNotification('Register successfull!');
          this.redirectToLogin();
        },
        error => {
          console.error('There was an error!', error);
          this.succes= error.httpStatus;
          if(this.succes === 200){
            this.notification.showNotification('Register successfull!');
            this.redirectToLogin();
          }
          else{
            this.errorMessage = error.error;
          }
        }
      );
  }
  public redirectToLogin():void{
    setTimeout(() => {
      this.router.navigate(['login']);
    }, 500);
  }
}
