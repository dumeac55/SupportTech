import { Component } from '@angular/core';
import { MechanicDto } from '../../model/mechanic-dto';
import { AppointmentService } from '../../service/appointment.service';
import { TypeDto } from '../../model/type-dto';
import { MechanicService } from '../../service/mechanic.service';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Router } from '@angular/router';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { ReviewService } from '../../service/review.service';
@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css'],
})
export class AppointmentComponent {
  mechanics: MechanicDto[] = [];
  types: TypeDto[] = [];
  orar: String[] = [];
  selectedMechanic: MechanicDto | null = null;
  selectedType: TypeDto | null = null;
  selectedDateTime: Date | null = null;
  selectedDateTimeFinal: Date | null = null;

  constructor(
    private appointmentService: AppointmentService,
    private mechanicService: MechanicService,
    private router : Router,
    private jwt : JwtStorageService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      this.getMechanics();
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  async getMechanics(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      try {
        const result = await this.appointmentService.getMechanics();
        const type = await this.appointmentService.getTypes();
        if (Array.isArray(result) && Array.isArray(type)) {
          this.mechanics = result;
          this.types = type;
        }
      } catch (error) {
        console.error('Error getting mechanics:', error);
      }
    }
    else{
      alert("Session Expired");
      this.redirectToLogin();
    }
  }

  selectMechanic(mechanic: MechanicDto): void {
    this.selectedMechanic = mechanic;
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
        this.selectedMechanic &&
        this.selectedType &&
        this.selectedDateTimeFinal
      ) {
        this.appointmentService
          .createAppointment(
            usernameUser,
            this.selectedMechanic.username as string,
            this.selectedType.nameType as string,
            this.selectedDateTimeFinal
          )
          .subscribe(
            (response) => {
              console.log('Appointment created successfully:', response);
              this.selectedMechanic = null;
              this.selectedType = null;
              this.selectedDateTime = null;
              this.redirectToProfile();
            },
            (error) => {
              if(error.status === 200){
                console.log('Appointment created successfully:');
                this.selectedMechanic = null;
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
        console.error('Please select mechanic, type, and date/time.');
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
    if (this.selectedDateTime && this.selectedMechanic) {
        try {
          const mechanicUsername = this.selectedMechanic.username;
          if (mechanicUsername) {
            const idMechanic = await this.mechanicService.getMechanicProfileByUsername(mechanicUsername);

            if (idMechanic !== undefined) {
              if( event.value){
                this.selectedDateTime = event.value;
                const dataString =  this.selectedDateTime.toLocaleDateString('en-CA', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-');
                console.log("data corecta" +this.selectedDateTime);
                console.log("data cu o zi in urma" + dataString);
                const orar = await this.appointmentService.getMechanicOrar(idMechanic, dataString as unknown as Date).toPromise();
                console.log('Orarul mecanicului:', orar);
                this.orar = orar;
              }
            } else {
              console.error('Mechanic id is undefined');
            }
          } else {
            console.error('Mechanic username is null');
          }
        } catch (error) {
          console.error('Error getting mechanic schedule:', error);
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
