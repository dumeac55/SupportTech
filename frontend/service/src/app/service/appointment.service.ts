import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private URL = 'http://localhost:8080/api/';
  constructor() {}
}
