import { Component, ViewChild } from '@angular/core';
import { AppointmentService } from '../../service/appointment.service';
import { TypeDto } from '../../model/type-dto';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Router } from '@angular/router';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { ReviewService } from '../../service/review.service';
import { TechnicianDto } from '../../model/technician-dto';
import { TechnicianService } from '../../service/technician.service';
import { NotificationService } from '../../service/notification.service';
import { MatTabGroup } from '@angular/material/tabs';
import { JwtDialogComponent } from '../jwt-dialog/jwt-dialog.component';
import { Dialog } from '@angular/cdk/dialog';
import { MatDialog } from '@angular/material/dialog';
import { SignInServiceService } from '../../service/sign-in-service.service';
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
  selectedInterval?: boolean = false;
  showLabels: boolean = false;
  @ViewChild('tabGroup') tabGroup: MatTabGroup | undefined;
  constructor(
    private appointmentService: AppointmentService,
    private technicianService: TechnicianService,
    private router: Router,
    private jwt: JwtStorageService,
    private notification: NotificationService,
    private dialog: MatDialog,
    private signInService: SignInServiceService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      this.getTechnicians();
    } else {
      const dialogRef = this.dialog.open(JwtDialogComponent);
      dialogRef.afterClosed().subscribe(() => {
        this.redirectToLogin();
        this.signInService.setIsLogged(false);
        localStorage.removeItem('token');
        localStorage.removeItem('isLogged');
      });
    }
  }

  async getTechnicians(): Promise<void> {
    const result = await this.appointmentService.getTechnicians();
    if (Array.isArray(result)) {
      this.technicians = result;
    }
  }

  selectTechnician(technician: TechnicianDto): void {
    this.selectedTechnician = technician;
    if (technician.username) {
      this.technicianService
        .getTechnicianProfileByUsername(technician.username)
        .then((idTechnician) => {
          if (idTechnician !== undefined) {
            this.appointmentService
              .getTypesByIdTechnician(idTechnician)
              .then((types) => {
                if (this.tabGroup) this.tabGroup.selectedIndex = 1;
                if (Array.isArray(types)) this.types = types;
              });
          }
        })
        .catch();
    }
  }

  selectType(type: TypeDto): void {
    this.selectedType = type;
    if (this.tabGroup) this.tabGroup.selectedIndex = 2;
  }

  AddAppointment(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      const usernameUser = localStorage.getItem('username');
      if (usernameUser === null) {
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
            this.selectedType.price as number,
            this.selectedDateTimeFinal
          )
          .subscribe(
            (response) => {
              this.selectedTechnician = null;
              this.selectedType = null;
              this.selectedDateTime = null;
              this.notification.showNotification(
                'Appointment create successfull!'
              );
              this.redirectToProfile();
            },
            (error) => {
              if (error.status === 200) {
                this.selectedTechnician = null;
                this.selectedType = null;
                this.selectedDateTime = null;
                this.notification.showNotification(
                  'Appointment create successfull!'
                );
                this.redirectToProfile();
              }
            }
          );
      }
    } else {
      const dialogRef = this.dialog.open(JwtDialogComponent);
      dialogRef.afterClosed().subscribe(() => {
        this.redirectToLogin();
        this.signInService.setIsLogged(false);
        localStorage.removeItem('token');
        localStorage.removeItem('isLogged');
      });
    }
  }

  async onDateTimeChange(event: MatDatepickerInputEvent<Date>): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
      if (this.selectedDateTime && this.selectedTechnician) {
        try {
          const technicianUsername = this.selectedTechnician.username;
          if (technicianUsername) {
            const idTechnician =
              await this.technicianService.getTechnicianProfileByUsername(
                technicianUsername
              );
            if (idTechnician !== undefined) {
              if (event.value) {
                this.selectedDateTime = event.value;
                const dataString = this.selectedDateTime
                  .toLocaleDateString('en-CA', {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                  })
                  .replace(/\//g, '-');
                const orar = await this.appointmentService
                  .getTechnicianOrar(
                    idTechnician,
                    dataString as unknown as Date
                  )
                  .toPromise();
                this.orar = orar;
              }
            }
          }
        } catch (error) {}
      }
    } else {
      const dialogRef = this.dialog.open(JwtDialogComponent);
      dialogRef.afterClosed().subscribe(() => {
        this.redirectToLogin();
        this.signInService.setIsLogged(false);
        localStorage.removeItem('token');
        localStorage.removeItem('isLogged');
      });
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
      this.selectedInterval = true;
    }
  }

  public filterHours = (d: Date | null): boolean => {
    const currentDate = new Date();
    const selectedDate = d || currentDate;
    if (selectedDate < currentDate) {
      return false;
    }
    const day = selectedDate.getDay();
    return day !== 0 && day !== 6;
  };

  redirectToProfile(): void {
    this.router.navigate(['/profile']);
  }
  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
