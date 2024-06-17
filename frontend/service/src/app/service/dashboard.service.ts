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

  getNoAppointmentsPerTechnician(year: number) : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year+ '/technician');
  }

  getNrAppontmentsPerTechnician(year: number): Observable<number[]> {
    return this.getNoAppointmentsPerTechnician(year).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.nrAppointments).filter((avg): avg is number => avg !== undefined))
    );
  }


  getFullNames(): Observable<string[]> {
    return this.getReviewByIdTechnician().pipe(
      map(dashboards => dashboards.map(dashboard => `${dashboard.firstName} ${dashboard.lastName}`))
    );
  }

  getNoAppointmentsPerUser(year: number, idProfile:number, status: string) : Observable<DashboardDTO[]>  {
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year + '/' + idProfile + "?status=" + status);
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

  getMoneyUser(year: number, idProfile: number): Observable<DashboardDTO[]>{
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year + '/' + idProfile + "/money");
  }

  getMoneysUser(year: number, idProfile:number): Observable<number[]> {
    return this.getMoneyUser(year, idProfile).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.totalMoney).filter((avg): avg is number => avg !== undefined))
    );
  }

  getAppointmentsByStatus(year: number): Observable<DashboardDTO[]>{
    return this.http.get<DashboardDTO[]>(this.URL + 'dashboard/' + year + '/status');
  }

  getTotalAppointments(year: number): Observable<number[]> {
    return this.getAppointmentsByStatus(year).pipe(
      map((dashboards: DashboardDTO[]) => dashboards.map(dashboard => dashboard.totalAppointments).filter((avg): avg is number => avg !== undefined))
    );
  }

  getStatus(year: number): Observable<string[]> {
    return this.getAppointmentsByStatus(year).pipe(
      map(dashboards => dashboards.map(dashboard => dashboard.status).filter((status): status is string => status !== undefined))
    );
  }
}
