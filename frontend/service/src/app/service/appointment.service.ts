import { Injectable } from '@angular/core';
import { MechanicDto } from '../model/mechanic-dto';
import { HttpClient } from '@angular/common/http';
import { TypeDto } from '../model/type-dto';
import { CreateAppointmentDto } from '../model/create-appointment-dto';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';
@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private URL = 'http://localhost:8080/api/';
  mechanicDto?:MechanicDto;
  constructor(private http:HttpClient, private datePipe:DatePipe) {}

  async getMechanics(): Promise <MechanicDto | undefined>{
    return await this.http.get<MechanicDto>(this.URL + "mechanic").toPromise();
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

  getMechanicOrar(id: number, date:Date) : Observable<any>{
    const formattedDate = this.datePipe.transform(date, 'yyyy-MM-dd');

    return this.http.get<any>(this.URL+ "/orar2?idMechanic=" + id +"&date="+ formattedDate);
  }

}
