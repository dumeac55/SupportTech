import {
  AfterViewInit,
  Component,
  ViewChild,
  ChangeDetectorRef,
} from '@angular/core';
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
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { NotificationService } from '../../service/notification.service';
import { TypeDto } from '../../model/type-dto';
import { forkJoin } from 'rxjs';
import { DashboardService } from '../../service/dashboard.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { DialogTypeComponent } from './dialog-type/dialog-type.component';
import { MatDialog } from '@angular/material/dialog';
import { TypeService } from '../../service/type.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  role?: String;

  userProfile: UserProfileDto = {};
  appointments: AppointmentDto[] = [];
  wishList: WishListDto[] = [];
  appointmentsTechnician: TechnicianAppointmentDto[] = [];
  types: TypeDto[] = [];

  haveAppointment: boolean = false;
  showAppointments: boolean = false;
  showWishlist: boolean = false;
  showServices: boolean = false;
  showStatistics: boolean = false;

  displayedColumns: string[] = [
    'Name',
    'Price',
    'Data',
    'Technician',
    'Phone',
    'Email',
    'Status',
    'Actions',
  ];
  displayedColumnsTechnician: string[] = [
    'Name',
    'Price',
    'Data',
    'Utilizator',
    'Phone',
    'Email',
    'Status',
    'Actions',
  ];
  displayedColumnsWishList: string[] = [
    'Name Product',
    'Price',
    'linkProduct',
    'linkImage',
    'Company',
    'Actions',
  ];

  dataSource = new MatTableDataSource<AppointmentDto>();
  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  yearNrAppointment?: number;
  barChartNoAppointmetnsPerUser: any;
  labelsNoAppointmetnsPerUser: string[] = [];
  datasetsANoAppointmetnsPerUser: number[] = [];
  availableYearsBarChart: number[] = [2023, 2024, 2025, 2026, 2027];
  availableStatusBarChart: string[] = [
    'All',
    'Done',
    'Canceled',
    'Pending',
    "Can't fix",
  ];
  status?: string;
  constructor(
    private jwtStorage: JwtStorageService,
    private userProfileService: UserProfileService,
    private appointmentService: AppointmentService,
    private technicianService: TechnicianService,
    private wishListService: WishListService,
    private router: Router,
    private notification: NotificationService,
    private dashboard: DashboardService,
    private dialog: MatDialog,
    private typeService: TypeService
  ) {
    this.status = 'all';
    this.yearNrAppointment = 2024;
  }
  ngOnInit() {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      this.getProfile();
    } else {
      alert('Session exirated');
      this.redirectToLogin();
    }
  }

  private async getProfile(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      try {
        this.role = await this.userProfileService.getRoleByUsername(
          localStorage.getItem('username')
        );
        if (this.role === 'custom') {
          const userProfile = await this.userProfileService.getUserProfile();
          const appointment =
            await this.userProfileService.getUserAppointment();
          const wishList = await this.wishListService.getWishlistForUser();
          if (userProfile) {
            this.userProfile = userProfile;
            if (Array.isArray(appointment)) {
              this.appointments = appointment;
              this.haveAppointment = true;
              this.dataSource.data = this.appointments;
              if (this.paginator) this.dataSource.paginator = this.paginator;
            } else {
              this.haveAppointment = false;
            }
            if (Array.isArray(wishList)) {
              this.wishList = wishList;
            }
          }
        } else if (this.role === 'technician') {
          const userProfile = await this.technicianService.getUserProfile();
          if (userProfile) {
            this.userProfile = userProfile;
            const appointment =
              await this.technicianService.getTechnicianAppointment();
            if (Array.isArray(appointment)) {
              this.appointmentsTechnician = appointment;
              this.haveAppointment = true;
            } else {
              this.haveAppointment = false;
            }
            this.technicianService
              .getTechnicianProfileByUsername(localStorage.getItem('username'))
              .then((idTechnician) => {
                if (idTechnician !== undefined) {
                  this.appointmentService
                    .getTypesByIdTechnician(idTechnician)
                    .then((types) => {
                      if (Array.isArray(types)) this.types = types;
                    });
                }
              })
              .catch();
          }
        }
      } catch (error) {}
    } else {
      alert('Session exirated');
      this.redirectToLogin();
    }
  }

  updateAppointmentStatus(id: number, newStatus: string): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwtStorage.isTokenExpired(token)) {
      this.appointmentService
        .updateAppointment(id, newStatus)
        .subscribe(() => this.updateLocalAppointmentStatus(id, newStatus));
    } else {
      alert('Session exirated');
      this.redirectToLogin();
    }
  }

  private updateLocalAppointmentStatus(id: number, newStatus: string): void {
    const appointment = this.appointments.find(
      (appt) => appt.idAppointment === id
    );
    const appointmentTechnician = this.appointmentsTechnician.find(
      (appt) => appt.idAppointment === id
    );
    if (appointment) {
      appointment.status = newStatus;
    }
    if (appointmentTechnician) {
      appointmentTechnician.status = newStatus;
    }
  }

  toggleView(view: string) {
    if (view === 'appointments') {
      this.showAppointments = true;
      this.showWishlist = false;
      this.showServices = false;
      this.showStatistics = false;
    } else if (view === 'wishlist') {
      this.showAppointments = false;
      this.showWishlist = true;
      this.showServices = false;
      this.showStatistics = false;
    } else if (view === 'service') {
      this.showServices = true;
      this.showAppointments = false;
      this.showWishlist = false;
      this.showStatistics = false;
    } else if (view === 'statistics') {
      this.showServices = false;
      this.showAppointments = false;
      this.showWishlist = false;
      this.showStatistics = true;
      this.appointmentPerYearPerUser();
    }
  }

  deleteProductFromWishList(id: number) {
    this.wishListService.deleteProductFromWishList(id).subscribe(
      () => {
        this.wishListService.getWishlistForUser();
        this.notification.showNotification('Item successfull removed');
      },
      (error) => {
        if (error.status === 200) {
          this.wishListService.getWishlistForUser();
          this.notification.showNotification('Item successfull removed');
          window.location.reload();
        }
      }
    );
  }

  async appointmentPerYearPerUser() {
    const idProfile = await this.userProfileService.getUserIdByUserName(
      localStorage.getItem('username')
    );
    if (idProfile && this.yearNrAppointment && this.status) {
      forkJoin({
        noAppointment: this.dashboard.getNoAppointmentsUser(
          this.yearNrAppointment,
          idProfile,
          this.status
        ),
        month: this.dashboard.getMonth(this.yearNrAppointment),
      }).subscribe(({ noAppointment, month }) => {
        this.datasetsANoAppointmetnsPerUser = noAppointment;
        this.labelsNoAppointmetnsPerUser = month;
        this.barChartNoAppointmetnsPerUser = {
          labels: this.labelsNoAppointmetnsPerUser,
          datasets: [
            {
              data: this.datasetsANoAppointmetnsPerUser,
              label: 'Nr Appointments Per User',
              backgroundColor: '#f88406',
            },
          ],
        };
      });
    }
  }

  onYearChange() {
    this.appointmentPerYearPerUser();
  }

  onStatusChange() {
    this.appointmentPerYearPerUser();
  }

  openDialog(type?: TypeDto): void {
    const dialogRef = this.dialog.open(DialogTypeComponent, {
      width: '250px',
      data: type
        ? { ...type }
        : { idType: null, nameType: '', price: 0, technicianId: null },
    });
    dialogRef.afterClosed().subscribe();
  }

  deleteType(type: TypeDto): void {
    if(type.idType)
    this.typeService.deleteType(type.idType).subscribe(
      () => {
        this.notification.showNotification("Type successfully deleted");
        this.types = this.types.filter(t => t.idType !== type.idType);
      },
      (error) => {
        if (error.status === 200) {
          this.notification.showNotification("Type successfully deleted");
          this.types = this.types.filter(t => t.idType !== type.idType);
        } else {
          console.error('Error deleting type:', error);
        }
      }
      );
  }

  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
