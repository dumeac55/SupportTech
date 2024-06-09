import { Injectable } from '@angular/core';
import { DashboardDTO } from '../model/dashboard-dto';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private URL = 'http://localhost:8080/api/';
  constructor(private http: HttpClient) { }

  getReviewByIdTechnician() : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/all');
  }

  getNoAppointments(year: number) : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year+ '/appointments');
  }

  getMonth(year: number): Observable<string[]> {
    return this.getNoAppointments(year).pipe(
      map(dashboards => dashboards.map(dashboard => this.convertMounthNumberToString(dashboard.month)))
    );
  }

  getNrAppontments(year: number): Observable<number[]> {
    return this.getNoAppointments(year).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.nrAppointments).filter((avg): avg is number => avg !== undefined))
    );
  }

  getAvgGrades(): Observable<number[]> {
    return this.getReviewByIdTechnician().pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.avgGrade).filter((avg): avg is number => avg !== undefined))
    );
  }

  getFullNames(): Observable<string[]> {
    return this.getReviewByIdTechnician().pipe(
      map(dashboards => dashboards.map(dashboard => `${dashboard.firstName} ${dashboard.lastName}`))
    );
  }

  convertMounthNumberToString(month: string | undefined): string {
    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    if(month){
    const monthNumber = parseInt(month, 10);
    return `${monthNames[monthNumber - 1]}`;
    }
    return '';
  }
}
