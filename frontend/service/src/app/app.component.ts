import { Component } from '@angular/core';
import { Observable, Observer } from 'rxjs';
import { JwtStorageService } from './service/jwt-storage.service';
import { Router } from '@angular/router';
import { SignInServiceService } from './service/sign-in-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  isLogged?: Boolean= false;
  constructor(
    private jwt : JwtStorageService,
    private router: Router
  ){}
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token) && localStorage.getItem('isLogged') === "true") {
      this.isLogged = true;
    }
    else{
      this.isLogged = false;
    }
  }
  logout(): void {
    this.isLogged = false;
    localStorage.removeItem('token');
    localStorage.removeItem('isLogged');
    this.router.navigate(['/home']);
  }

  title = 'service';
}
