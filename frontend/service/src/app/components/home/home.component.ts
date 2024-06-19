import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SignInServiceService } from '../../service/sign-in-service.service';
import { MatDialog } from '@angular/material/dialog';
import { SignInComponent } from '../sign-in/sign-in.component';
import { HomeDialogComponent } from './home-dialog/home-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  get IsLogged(){
    return this.signInService.getIsLogged();
  }

  constructor(private router: Router,
              private signInService: SignInServiceService,
              private dialog: MatDialog
  ){}

  bookNow() {
    if (this.IsLogged) {
      this.redirectToAppointments();
    } else {
      const dialogRef = this.dialog.open(HomeDialogComponent);

      dialogRef.afterClosed().subscribe(() => {
        this.redirectToLogin();
      });
    }
  }

  learnMore(){
    this.redirectToReview();
  }

  public redirectToLogin(): void{
    this.router.navigate(['/login']);
  }

  public redirectToAppointments(): void{
    this.router.navigate(['/appointment']);
  }

  public redirectToReview(): void{
    this.router.navigate(['/review']);
  }
}
