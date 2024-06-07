import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { AppointmentDto } from '../../model/appointment-dto';
import { ReviewDto } from '../../model/review-dto';
import { count } from 'rxjs';
import { ReviewService } from '../../service/review.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent{
  reviewDto: ReviewDto[] = [];
  lineChartData: any;
  constructor(private review: ReviewService) { }
  ngOnInit(): void {
    this.calculateAppointmentCounts();
    this.asd();
  }
  async asd() {
    const counts = await this.calculateAppointmentCounts();
    this.lineChartData = {
      labels: ["Jan", "Feb", "May", "Apr", "Jun", "July", "Aug"],
      datasets: [
        {
          data: counts,
          label: 'Sales Percent',
          fill: true,
          backgroundColor: 'rgba(255, 255, 0, 0.3)',
          borderColor: 'black',
          tension: 0.5
        }
      ]
    };
  }
  barChartData = {
    labels: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    datasets: [
      {
        data: [89, 34, 43, 54, 28, 74, 93],
        label: 'Sales Percent',
        backgroundColor: '#f88406'
      }
    ]
  }
  async calculateAppointmentCounts(): Promise<number[]> {
    let counts = [0, 0, 0, 0, 0, 0, 0];
    const data = await this.review.getReviewByIdTechnician(1).toPromise();
    if(data)
    this.reviewDto = data;
    this.reviewDto.forEach((appointment) => {
      if (appointment.avgGrade) {
        counts[3] += appointment.avgGrade; // Dacă vrei să aduni valoarea la indicele 3 pentru fiecare programare, așa cum ai făcut în exemplul tău
      }
    });
    console.log(counts); // Log the counts after they've been calculated
    return counts;
  }
}
