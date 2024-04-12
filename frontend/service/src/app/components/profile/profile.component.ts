import { Component } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { UserProfileService } from '../../service/user-profile.service';
import { UserProfileDto } from '../../model/user-profile-dto';
import { AppointmentDto } from '../../model/appointment-dto';
import { AppointmentService } from '../../service/appointment.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  userProfile: UserProfileDto = {};
  constructor(private jwtStorage: JwtStorageService, private userProfileService: UserProfileService, private appointmentService:AppointmentService){}
  appointments: AppointmentDto[] = [];
  ngOnInit(){
    this.getProfile();
  }
  private async getProfile(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      try {
        const userProfile = await this.userProfileService.getUserProfile();
        const appointment = await this.userProfileService.getUserAppointment();

        if (userProfile) {
          if (Array.isArray(appointment)) {
            this.appointments = appointment;
            console.log('Appointments loaded successfully:', appointment);
          } else {
            console.log('Appointments not found or not in correct format.');
          }
          this.userProfile = userProfile;
          console.log('Profile loaded successfully:', userProfile);
          console.log('Appointments loaded:', appointment);
        } else {
          console.log('Failed to load profile.');
        }
      } catch (error) {
        console.error('Error loading profile:', error);
      }
    } else {
      console.log('Token is expired or missing. Redirecting to login page...');
    }
  }

  updateAppointmentStatus(id: number, newStatus: string): void {
    this.appointmentService.updateAppointment(id, newStatus)
      .subscribe(
        response => {
          console.log('Appointment updated successfully:', response);
          // Actualizați lista de întâlniri sau faceți alte acțiuni necesare
        },
        error => {
          console.error('Error updating appointment:', error);
          // Tratați eroarea corespunzător
        }
      );
  }

}
