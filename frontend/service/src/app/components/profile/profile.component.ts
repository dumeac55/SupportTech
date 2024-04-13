import { Component } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { UserProfileService } from '../../service/user-profile.service';
import { UserProfileDto } from '../../model/user-profile-dto';
import { AppointmentDto } from '../../model/appointment-dto';
import { AppointmentService } from '../../service/appointment.service';
import { MechanicService } from '../../service/mechanic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  userProfile: UserProfileDto = {};
  constructor(private jwtStorage: JwtStorageService, private userProfileService: UserProfileService, private appointmentService:AppointmentService, private mechanicService: MechanicService){}
  appointments: AppointmentDto[] = [];
  ngOnInit(){
    this.getProfile();
  }
  private async getProfile(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      try {
        const role = await this.userProfileService.getRoleByUsername(localStorage.getItem('username'));
        if(role === 'custom'){
          const userProfile = await this.userProfileService.getUserProfile();
          const appointment = await this.userProfileService.getUserAppointment();
          console.log(userProfile?.role);
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
        }
        else{
          const userProfile = await this.mechanicService.getUserProfile();
          const appointment = await this.mechanicService.getMechanicAppointment();
          console.log(userProfile?.role);
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
        }
        
        
      } catch (error) {
        console.error('Error loading profile:', error);
      }
    } else {
      console.log('Token is expired or missing. Redirecting to login page...');
    }
  }

  updateAppointmentStatus(id: number, newStatus: string): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
    this.appointmentService.updateAppointment(id, newStatus)
      .subscribe(
        response => {
          console.log('Appointment updated successfully:', response);
          window.location.reload();
        },
        error => {
          console.error('Error updating appointment:', error);
        }
      );
    } else {
      console.log('Token is expired or missing. Redirecting to login page...');
    }
  }

}
