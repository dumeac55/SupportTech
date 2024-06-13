import { Component, OnInit } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import {Router} from '@angular/router'
import { UserProfileService } from '../../service/user-profile.service';
import { SignInDto } from '../../model/sign-in-dto';
import { SignInServiceService } from '../../service/sign-in-service.service';
import { NotificationService } from '../../service/notification.service';


@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  signinDto: SignInDto = {}
  errorMessage: string = '';

  constructor(
    private jwt: JwtStorageService,
    private router:Router,
    private userProfile: UserProfileService,
    private notification: NotificationService,
    private signInService:SignInServiceService
  ) { }

  ngOnInit() {
  }

  public signIn(): void {
    this.errorMessage = '';
    this.jwt.generateToken(this.signinDto).subscribe(
      (data: any) => {
        this.jwt.setTokenStorage(data, this.signinDto.username);
        localStorage.setItem('isLogged', "true");
        this.signInService.setIsLogged(true);
        this.notification.showNotification('Log in successfull!');
        this.redirectToProfile();
      },
      error => {
        if (error.status === 200) {
          localStorage.setItem('isLogged', "true");
          this.signInService.setIsLogged(true);
          this.notification.showNotification('Login successfull!');
          this.redirectToProfile();

        } else {
          this.errorMessage = error.error;
        }
      }
    );
  }

  private redirectToProfile(): void{
    setTimeout(() => {
      this.router.navigate(['/profile']);
    }, 300);
  }
  public redirectToRegister(): void{
    this.router.navigate(['/register']);
  }
}
