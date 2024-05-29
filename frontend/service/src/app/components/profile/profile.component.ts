import { Component } from '@angular/core';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { UserProfileService } from '../../service/user-profile.service';
import { UserProfileDto } from '../../model/user-profile-dto';
import { AppointmentDto } from '../../model/appointment-dto';
import { AppointmentService } from '../../service/appointment.service';
import { Router } from '@angular/router';
import { TechnicianService } from '../../service/technician.service';
import { TechnicianAppointmentDto } from '../../model/technician-appointment-dto';
import { WishListDto } from '../../model/wish-list-dto';
import { WishListService } from '../../service/wish-list.service';
import { SignInServiceService } from '../../service/sign-in-service.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  userProfile: UserProfileDto = {};
  haveAppointment: boolean = false;
  appointments: AppointmentDto[] = [];
  wishList: WishListDto [] = [];
  appointmentsTechnician: TechnicianAppointmentDto[] = [];
  role?: String;
  showAppointments: boolean = false;
  showWishlist: boolean = false;
  displayedColumns: string[] = ['Nume', 'Data', 'Technician', 'Phone', 'Email', 'Status', 'Actions'];
  constructor(private jwtStorage: JwtStorageService,
              private userProfileService: UserProfileService,
              private appointmentService: AppointmentService,
              private technicianService: TechnicianService,
              private wishListService: WishListService,
              private router: Router,
              private signInServie: SignInServiceService){}

  ngOnInit(){
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      this.getProfile();
    }
    else {
      alert("Session exirated");
      this.redirectToLogin();
    }
  }

  private async getProfile(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      try {
        this.role = await this.userProfileService.getRoleByUsername(localStorage.getItem('username'));
        if(this.role === 'custom'){
          const userProfile = await this.userProfileService.getUserProfile();
          const appointment = await this.userProfileService.getUserAppointment();
          const wishList = await this.wishListService.getWishlistForUser();
          console.log(userProfile?.role);
          if (userProfile) {
            this.userProfile = userProfile;
            if (Array.isArray(appointment)) {
                this.appointments = appointment;
                this.haveAppointment = true;
                console.log('Appointments loaded successfully:', appointment);
            } else {
                this.haveAppointment = false;
                console.log('Appointments not found or not in correct format.');
            }
            if (Array.isArray(wishList)) {
              this.wishList = wishList;
            }
            console.log('Profile loaded successfully:', userProfile);
          } else {
            console.log('Failed to load profile.');
          }
        }
        else if (this.role ==='technician')
          {
          const userProfile = await this.technicianService.getUserProfile();
          console.log(userProfile?.role);
          if (userProfile) {
            this.userProfile = userProfile;
            const appointment = await this.technicianService.getTechnicianAppointment();
            if (Array.isArray(appointment)) {
              this.appointmentsTechnician = appointment;
              console.log('Appointments loaded successfully:', appointment);
              this.haveAppointment = true;
            } else {
              console.log('Appointments not found or not in correct format.');
              this.haveAppointment = false;
            }
            console.log('Profile loaded successfully:', userProfile);
            console.log('Appointments loaded:', appointment);
          } else {
            console.log('Failed to load profile.');
          }
        }
      } catch (error) {
        console.error('Error loading profile:', error);
      }
    }
    else {
      alert("Session exirated");
      this.redirectToLogin();
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
    }
    else {
      alert("Session exirated");
      this.redirectToLogin();
    }
  }

  toggleView(view: string) {
    if (view === 'appointments') {
      this.showAppointments = true;
      this.showWishlist = false;
    } else if (view === 'wishlist') {
      this.showAppointments = false;
      this.showWishlist = true;
    }
  }

  redirectToLogin():void{
    this.router.navigate(['/login']);
  }

}
