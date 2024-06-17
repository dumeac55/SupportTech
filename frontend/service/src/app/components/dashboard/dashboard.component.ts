import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { AppointmentDto } from '../../model/appointment-dto';
import { ReviewDto } from '../../model/review-dto';
import { count, forkJoin } from 'rxjs';
import { ReviewService } from '../../service/review.service';
import { DashboardService } from '../../service/dashboard.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent{
  reviewDto: ReviewDto[] = [];
  barChartAvgTechnicians: any;
  labelsAvgTechnicians: string[] = [];
  datasetsAvgTechnicians: number[] = [];

  yearNrAppointment: number = 2024;
  lineChartNoAppointmets: any;
  labelNoAppointments: string[] = [];
  datasetsNoAppointmets: number[] = [];
  availableYears: number[] = [2023, 2024, 2025, 2026, 2027];

  barChartNoAppointmetnsPerTechnicians: any;
  labelsNoAppointmetnsPerTechnicians: string[] = [];
  datasetsANoAppointmetnsPerTechnicians: number[] = [];
  availableYearsBarChart: number[] = [2023, 2024, 2025, 2026, 2027];

  pieChartStatus: any;
  labelStatus: string[] = [];
  datasetsStatus: number[] = [];
  availableYearsStatus: number[] = [2023, 2024, 2025, 2026, 2027];

  constructor(private review: ReviewService,
              private dashboard: DashboardService
  ) { }
  ngOnInit(): void {
    this.avgTechnicians();
    this.appointmentPerYear();
    this.appointmentPerYearPerTechnician();
    this.appointmentByStatus();
  }

  avgTechnicians() {
    forkJoin({
      avgGrades: this.dashboard.getAvgGrades(),
      fullNames: this.dashboard.getFullNames()
    }).subscribe(({ avgGrades, fullNames }) => {
      this.datasetsAvgTechnicians = avgGrades;
      this.labelsAvgTechnicians = fullNames;
      this.barChartAvgTechnicians = {
        labels: this.labelsAvgTechnicians,
        datasets: [
          {
            data: this.datasetsAvgTechnicians,
            label: 'Review' ,
            backgroundColor: '#17A2B8'
          }
        ]
      };
    });
  }

  appointmentPerYear(){
    forkJoin({
      month: this.dashboard.getMonth(this.yearNrAppointment),
      nrAppontments: this.dashboard.getNrAppontments(this.yearNrAppointment)
    }).subscribe(({ month, nrAppontments }) => {
      this.datasetsNoAppointmets = nrAppontments;
      this.labelNoAppointments = month;
      this.lineChartNoAppointmets = {
        labels: this.labelNoAppointments,
        datasets: [
          {
            data: this.datasetsNoAppointmets,
            label: 'Nr Appointments' ,
            backgroundColor: '#17A2B8'
          }
        ]
      };
    });
  }

  appointmentPerYearPerTechnician(){
    forkJoin({
      noAppointment: this.dashboard.getNrAppontmentsPerTechnician(this.yearNrAppointment),
      fullNames: this.dashboard.getFullNamesTechnicians(this.yearNrAppointment)
    }).subscribe(({ noAppointment, fullNames }) => {
      this.datasetsANoAppointmetnsPerTechnicians = noAppointment;
      this.labelsNoAppointmetnsPerTechnicians = fullNames;
      this.barChartNoAppointmetnsPerTechnicians = {
        labels: this.labelsNoAppointmetnsPerTechnicians,
        datasets: [
          {
            data: this.datasetsANoAppointmetnsPerTechnicians,
            label: 'Nr Appointments Per Technician' ,
            backgroundColor: '#17A2B8'
          }
        ]
      };
    });
  }
  

  appointmentByStatus(){
    forkJoin({
      noAppointment: this.dashboard.getTotalAppointments(this.yearNrAppointment),
      Status: this.dashboard.getStatus(this.yearNrAppointment)
    }).subscribe(({ noAppointment, Status }) => {
      this.datasetsStatus = noAppointment;
      this.labelStatus = Status;
      this.pieChartStatus = {
        labels: this.labelStatus,
        datasets: [
          {
            data: this.datasetsStatus,
            label: 'Status' ,
            backgroundColor: '#17A2B8',
          }
        ]
      };
    });
  }

  onYearChange() {
    this.appointmentPerYear();
  }

  onYearChange2() {
    this.appointmentPerYearPerTechnician();
  }
}
