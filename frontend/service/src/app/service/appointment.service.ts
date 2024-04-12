import { Injectable } from '@angular/core';
import { MechanicDto } from '../model/mechanic-dto';
import { HttpClient } from '@angular/common/http';
import { TypeDto } from '../model/type-dto';
import { CreateAppointmentDto } from '../model/create-appointment-dto';
import { Observable } from 'rxjs';
import { AppointmentDto } from '../model/appointment-dto';
@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private URL = 'http://localhost:8080/api/';
  mechanicDto?:MechanicDto;
  constructor(private http:HttpClient) {}

  async getMechanics(): Promise <MechanicDto | undefined>{
    return await this.http.get<MechanicDto>(this.URL + "mechanics").toPromise();
  }

  async getTypes(): Promise <TypeDto | undefined>{
    return await this.http.get<TypeDto>(this.URL + "utils/types").toPromise();
  }

  createAppointment(usernameUser: string, usernameMechanic: string, type: string, data: Date): Observable<any> {
    const appointmentDto: CreateAppointmentDto = {
        usernameUser: usernameUser,
        usernameMechanic: usernameMechanic,
        type: type,
        data: data
    };

    return this.http.post<any>(this.URL + "appointment/create", appointmentDto);
  }

  updateAppointment(id: number, newStatus: string): Observable<any> {
    const updateAppointment = {
      idAppointment: id,
      status: newStatus
    };
    return this.http.post<any>(this.URL + 'appointment/update', updateAppointment);
  }


}
