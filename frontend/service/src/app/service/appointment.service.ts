import { Injectable } from '@angular/core';
import { MechanicDto } from '../model/mechanic-dto';
import { HttpClient } from '@angular/common/http';

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
}
