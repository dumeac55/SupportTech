import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TypeDto } from '../model/type-dto';
import { CreateAppointmentDto } from '../model/create-appointment-dto';
import { Observable } from 'rxjs';
import { TechnicianDto } from '../model/technician-dto';
@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private URL = 'http://localhost:8080/api/';
  technicianDto?: TechnicianDto;
  constructor(private http: HttpClient) {}

  async getTechnicians(): Promise<TechnicianDto | undefined> {
    return await this.http.get<TechnicianDto>(this.URL + 'technician').toPromise();
  }

  async getTypes(): Promise<TypeDto | undefined> {
    return await this.http.get<TypeDto>(this.URL + 'utils/types').toPromise();
  }
  async getTypesByIdTechnician(id: number): Promise<TypeDto | undefined> {
    return await this.http.get<TypeDto>(this.URL + 'utils/type/'+ id).toPromise();
  }

  createAppointment(
    usernameUser: string,
    usernameTechnician: string,
    type: string,
    data: Date
  ): Observable<any> {
    const appointmentDto: CreateAppointmentDto = {
      usernameUser: usernameUser,
      usernameTechnician: usernameTechnician,
      type: type,
      data: data,
    };

    return this.http.post<any>(this.URL + 'appointment/create', appointmentDto);
  }

  updateAppointment(id: number, newStatus: string): Observable<any> {
    const updateAppointment = {
      idAppointment: id,
      status: newStatus,
    };
    return this.http.post<any>(
      this.URL + 'appointment/update',
      updateAppointment
    );
  }

  getTechnicianOrar(id: number, date: Date): Observable<any> {
    return this.http.get<any>(
      this.URL + 'appointment/orar2?idTechnician=' + id + '&date=' + date
    );
  }
}
