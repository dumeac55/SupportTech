import { Component } from '@angular/core';
import { AppointmentService } from '../../service/appointment.service';
import { TypeDto } from '../../model/type-dto';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Router } from '@angular/router';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { ReviewService } from '../../service/review.service';
import { TechnicianDto } from '../../model/technician-dto';
import { TechnicianService } from '../../service/technician.service';
@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css'],
})
export class AppointmentComponent {
  technicians: TechnicianDto[] = [];
  types: TypeDto[] = [];
  orar: String[] = [];
  selectedTechnician: TechnicianDto | null = null;
  selectedType: TypeDto | null = null;
  selectedDateTime: Date | null = null;
  selectedDateTimeFinal: Date | null = null;

  constructor(
    private appointmentService: AppointmentService,
    private technicianService: TechnicianService,
    private router : Router,
    private jwt : JwtStorageService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      this.getTechnicians();
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  async getTechnicians(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      try {
        const result = await this.appointmentService.getTechnicians();
        if (Array.isArray(result)) {
          this.technicians = result;
        }
      } catch (error) {
        console.error('Error getting technicians:', error);
      }
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  selectTechnician(technician: TechnicianDto): void {
    this.selectedTechnician = technician;
    if (technician.username) {
      this.technicianService.getTechnicianProfileByUsername(technician.username).then(
        (idTechnician) => {
          if (idTechnician !== undefined) {
            this.appointmentService.getTypesByIdTechnician(idTechnician).then(
              (types) => {
                if(Array.isArray(types))
                this.types = types;
              },
              (error: string) => {
                console.error('Error getting types for technician:', error);
              }
            );
          } else {
            console.error('Technician id is undefined');
          }
        }
      ).catch(
        (error) => {
          console.error('Error getting technician ID:', error);
        }
      );
    } else {
      console.error('Selected technician does not have a valid username');
    }
  }

  selectType(type: TypeDto): void {
    this.selectedType = type;
  }

  AddAppointment(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      const usernameUser = localStorage.getItem('username');
      if (usernameUser === null) {
        console.error('Username not found in localStorage.');
        return;
      }
      if (
        this.selectedTechnician &&
        this.selectedType &&
        this.selectedDateTimeFinal
      ) {
        this.appointmentService
          .createAppointment(
            usernameUser,
            this.selectedTechnician.username as string,
            this.selectedType.nameType as string,
            this.selectedDateTimeFinal
          )
          .subscribe(
            (response) => {
              console.log('Appointment created successfully:', response);
              this.selectedTechnician = null;
              this.selectedType = null;
              this.selectedDateTime = null;
              this.redirectToProfile();
            },
            (error) => {
              if(error.status === 200){
                console.log('Appointment created successfully:');
                this.selectedTechnician = null;
                this.selectedType = null;
                this.selectedDateTime = null;
                this.redirectToProfile();
              }
              else{
                console.error('Error creating appointment:', error);
              }
            }
          );
      } else {
        console.error('Please select technician, type, and date/time.');
      }
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  async onDateTimeChange(event: MatDatepickerInputEvent<Date>): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
    if (this.selectedDateTime && this.selectedTechnician) {
        try {
          const technicianUsername = this.selectedTechnician.username;
          if (technicianUsername) {
            const idTechnician = await this.technicianService.getTechnicianProfileByUsername(technicianUsername);
            if (idTechnician !== undefined) {
              if( event.value){
                this.selectedDateTime = event.value;
                const dataString =  this.selectedDateTime.toLocaleDateString('en-CA', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-');
                console.log("data corecta" +this.selectedDateTime);
                console.log("data cu o zi in urma" + dataString);
                const orar = await this.appointmentService.getTechnicianOrar(idTechnician, dataString as unknown as Date).toPromise();
                console.log('Orarul mecanicului:', orar);
                this.orar = orar;
              }
            } else {
              console.error('Technician id is undefined');
            }
          } else {
            console.error('Technician username is null');
          }
        } catch (error) {
          console.error('Error getting technician schedule:', error);
        }
      }
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  setSelectedDateTimeFinal(interval: string | String): void {
    if (this.selectedDateTime) {
      const year = this.selectedDateTime.getFullYear();
      const month = this.selectedDateTime.getMonth() + 1;
      const day = this.selectedDateTime.getDate();
      const hours = parseInt(interval.split(':')[0], 10);
      const minutes = parseInt(interval.split(':')[1], 10);
      const formattedDateTime = `${year}/${month}/${day} ${hours}:${minutes}`;
      this.selectedDateTimeFinal = new Date(formattedDateTime);
    }
  }

  public myFilter = (d: Date | null): boolean => {
    const currentDate = new Date();
    const selectedDate = d || currentDate;
    if (selectedDate < currentDate) {
        return false;
    }
    const day = selectedDate.getDay();
    return day !== 0 && day !== 6;
};

  redirectToProfile(): void{
    this.router.navigate(['/profile']);
  }
  redirectToLogin(): void{
    this.router.navigate(['/login']);
  }
}
