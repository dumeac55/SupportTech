import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TechnicianDto } from '../model/technician-dto';
import { TechnicianAppointmentDto } from '../model/technician-appointment-dto';
import { TypeDto } from '../model/type-dto';

@Injectable({
  providedIn: 'root'
})
export class TechnicianService {

  private URL = 'http://localhost:8080/api/';
  private technicianProfile?: TechnicianDto;
  private id?: number;

  constructor(private http:HttpClient) { }

  async getUserProfile(): Promise <TechnicianDto | undefined>{
    this.id = await this.getUserIdByUserName(localStorage.getItem('username'));
    return await this.http.get<TechnicianDto>(this.URL + "technician/" + this.id).toPromise();
  }

  async getRoleByUsername(username: any): Promise <string | undefined>{
    return await this.http.get<string>(this.URL + "user/username=" + username + "/role", {responseType: 'text' as 'json'}).toPromise();
  }
  async getUserIdByUserName(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "user/username=" + username).toPromise();
  }
  async getTechnicianProfileByUsername(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "technician/username=" + username).toPromise();
  }

  async getTechnicianAppointment(): Promise <TechnicianAppointmentDto | undefined>{
    this.id = await this.getTechnicianProfileByUsername(localStorage.getItem('username'));
    return await this.http.get<TechnicianAppointmentDto>(this.URL + "technician/" + this.id + "/appointment").toPromise();
  }

  async addType(type : TypeDto) : Promise <void>{
    this.http.post<TypeDto>(this.URL + 'utils/type', type,{ responseType: 'text' as 'json' }).subscribe();
  }

  async getTypesByIdTechnician(id: number): Promise<TypeDto | undefined> {
    return await this.http.get<TypeDto>(this.URL + 'utils/type'+ id).toPromise();
  }
}
