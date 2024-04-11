import { Component } from '@angular/core';
import { MechanicDto } from '../../model/mechanic-dto';
import { AppointmentService } from '../../service/appointment.service';
import { TypeDto } from '../../model/type-dto';
import { CalendarEvent } from 'angular-calendar';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.css'
})
export class AppointmentComponent {
  mechanics: MechanicDto[] = [];
  types: TypeDto[] = [];
  selectedMechanic: MechanicDto | null = null;
  selectedType: TypeDto | null = null;
  selectedDateTime: Date | null = null;
  constructor(private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.getMechanics();
  }

  async getMechanics(): Promise<void> {
    try {
      const result = await this.appointmentService.getMechanics();
      const type = await this.appointmentService.getTypes();
      if (Array.isArray(result) && (Array.isArray(type))) {
        this.mechanics = result;
        this.types = type;
      }
    } catch (error) {
      console.error('Error getting mechanics:', error);
    }
  }

  selectMechanic(mechanic: MechanicDto): void {
    this.selectedMechanic = mechanic;
  }

  selectType(type: TypeDto): void {
    this.selectedType = type;
  }

  AddAppointment():void{
    const usernameUser = localStorage.getItem('username');
    if (usernameUser === null ) {
      console.error('Username not found in localStorage.');
      return;
    }
    if (this.selectedMechanic && this.selectedType && this.selectedDateTime) {
      this.appointmentService.createAppointment(usernameUser,this.selectedMechanic.username as string, this.selectedType.nameType as string, this.selectedDateTime).subscribe(
        (response) => {
          // Procesați răspunsul primit de la serviciu, dacă este necesar
          console.log('Appointment created successfully:', response);
          // Resetarea câmpurilor după adăugarea programării
          this.selectedMechanic = null;
          this.selectedType = null;
          this.selectedDateTime = null;
        },
        (error) => {
          console.error('Error creating appointment:', error);
        }
      );
    } else {
      console.error('Please select mechanic, type, and date/time.');
    }
  }
  
}
