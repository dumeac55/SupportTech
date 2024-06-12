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

  getNoAppointmentsPerTechnician(year: number) : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year+ '/technician');
  }

  getNoAppointmentsPerUser(year: number, idProfile:number, status: string) : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year + '/' + idProfile + "?status=" + status);
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

  getNrAppontmentsPerTechnician(year: number): Observable<number[]> {
    return this.getNoAppointmentsPerTechnician(year).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.nrAppointments).filter((avg): avg is number => avg !== undefined))
    );
  }

  getFullNamesTechnicians(year: number): Observable<string[]> {
    return this.getNoAppointmentsPerTechnician(year).pipe(
      map(dashboards => dashboards.map(dashboard => `${dashboard.firstName} ${dashboard.lastName}`))
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

  getMonthUser(year: number, idProfile:number, status: string): Observable<string[]> {
    return this.getNoAppointmentsPerUser(year, idProfile, status).pipe(
      map(dashboards => dashboards.map(dashboard => this.convertMounthNumberToString(dashboard.month)))
    );
  }

  getNoAppointmentsUser(year: number, idProfile:number, status: string): Observable<number[]> {
    return this.getNoAppointmentsPerUser(year, idProfile, status).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.nrAppointments).filter((avg): avg is number => avg !== undefined))
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
