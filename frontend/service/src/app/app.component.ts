import { Component } from '@angular/core';
import { Observable, Observer } from 'rxjs';
import { JwtStorageService } from './service/jwt-storage.service';
export interface ExampleTab {
  label: string;
  content: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  isLogged : Boolean = false;
  constructor(private jwt : JwtStorageService){}
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      this.isLogged = true;
    }
  }

  title = 'service';
}
