import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MechanicDto } from '../model/mechanic-dto';
import { AppointmentDto } from '../model/appointment-dto';
import { MechanicAppointmentDto } from '../model/mechanic-appointment-dto';
@Injectable({
  providedIn: 'root'
})
export class MechanicService {

  private URL = 'http://localhost:8080/api/';
  private mechanicProfile?: MechanicDto;
  private id?: number;

  constructor(private http:HttpClient) { }

  async getUserProfile(): Promise <MechanicDto | undefined>{
    this.id = await this.getUserIdByUserName(localStorage.getItem('username'));
    return await this.http.get<MechanicDto>(this.URL + "mechanic/" + this.id).toPromise();
  }

  async getRoleByUsername(username: any): Promise <string | undefined>{
    return await this.http.get<string>(this.URL + "user/username=" + username + "/role", {responseType: 'text' as 'json'}).toPromise();
  }
  async getUserIdByUserName(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "user/username=" + username).toPromise();
  }
  async getMechanicProfileByUsername(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "mechanic/username=" + username).toPromise();
  }

  async getMechanicAppointment(): Promise <MechanicAppointmentDto | undefined>{
    this.id = await this.getMechanicProfileByUsername(localStorage.getItem('username'));
    return await this.http.get<MechanicAppointmentDto>(this.URL + "mechanic/" + this.id + "/appointment").toPromise();
  }

}
