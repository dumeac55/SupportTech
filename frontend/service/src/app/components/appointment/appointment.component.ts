import { Component } from '@angular/core';
import { MechanicDto } from '../../model/mechanic-dto';
import { AppointmentService } from '../../service/appointment.service';
@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.css'
})
export class AppointmentComponent {
  mechanics: MechanicDto[] = [];

  constructor(private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.getMechanics();
  }

  async getMechanics(): Promise<void> {
    try {
      const result = await this.appointmentService.getMechanics();
      if (Array.isArray(result)) {
        this.mechanics = result;
      }
    } catch (error) {
      console.error('Error getting mechanics:', error);
    }
  }
}
