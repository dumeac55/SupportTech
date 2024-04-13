import { Component, OnInit } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import {Router} from '@angular/router'
import { UserProfileService } from '../../service/user-profile.service';
import { SignInDto } from '../../model/sign-in-dto';


@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  signinDto: SignInDto = {}
  errorMessage: string = '';

  constructor(private jwt: JwtStorageService, private router:Router, private userProfile: UserProfileService) { }

  ngOnInit() {
  }

  public signIn(): void {
    this.errorMessage = '';
    this.jwt.generateToken(this.signinDto).subscribe(
      (data: any) => {
        console.log("Token:", data);
        this.jwt.setTokenStorage(data, this.signinDto.username);
        this.redirectToProfile();
      },
      error => {
        console.error("Error:", error);
        if (error.status === 200) {
          this.redirectToProfile();
        } else {
          this.errorMessage = error.error;
        }
      }
    );
  }

  private redirectToProfile(): void{
    this.router.navigate(['/profile'])
  }
}
