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
  constructor(private review: ReviewService,
              private dashboard: DashboardService
  ) { }
  ngOnInit(): void {
    this.avgTechnicians();
    this.appointmentPerYear();
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
            backgroundColor: '#f88406'
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
            backgroundColor: '#f88406'
          }
        ]
      };
    });
  }

  onYearChange() {
    this.appointmentPerYear();
  }
}
