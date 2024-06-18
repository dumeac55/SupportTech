import { Component } from '@angular/core';
import { Observable, Observer } from 'rxjs';
import { JwtStorageService } from './service/jwt-storage.service';
import { Router } from '@angular/router';
import { SignInServiceService } from './service/sign-in-service.service';
import { NotificationService } from './service/notification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  message: string | null = null;

  get IsLogged(){
    return this.signInService.getIsLogged();
  }


  constructor(
    private jwt : JwtStorageService,
    private router: Router,
    private signInService: SignInServiceService
  ){}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token) && localStorage.getItem('isLogged') === "true") {
      this.signInService.setIsLogged(true);
    }
    else{
      this.signInService.setIsLogged(false);
    }
  }

  logout(): void {
    this.signInService.setIsLogged(false);
    localStorage.removeItem('token');
    localStorage.removeItem('isLogged');
    this.router.navigate(['/home']);
  }

  title = 'service';
}
