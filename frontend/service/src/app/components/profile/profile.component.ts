import { Component } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { UserProfileService } from '../../service/user-profile.service';
import { UserProfileDto } from '../../model/user-profile-dto';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  userProfile?: UserProfileDto;
  constructor(private jwtStorage: JwtStorageService, private userProfileService: UserProfileService){}

  ngOnInit(){
    this.getProfile();
  }
  private async getProfile(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      try {
        const userProfile = await this.userProfileService.getUserProfile();
        if (userProfile) {
          this.userProfile = userProfile;
          console.log('Profilul a fost încărcat:', userProfile);
        } else {
          console.log('Nu s-a putut încărca profilul.');
        }
      } catch (error) {
        console.error('Eroare la încărcarea profilului:', error);
      }
    } else {
      console.log('Tokenul este expirat sau lipsă. Redirecționare către pagina de autentificare...');
    }
  }
}
