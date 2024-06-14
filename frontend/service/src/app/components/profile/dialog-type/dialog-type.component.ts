import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TypeService } from '../../../service/type.service';
import { TechnicianService } from '../../../service/technician.service';
import { NotificationService } from '../../../service/notification.service';
@Component({
  selector: 'app-dialog-type',
  templateUrl: './dialog-type.component.html',
  styleUrl: './dialog-type.component.css',
})
export class DialogTypeComponent {
  constructor(
    public dialogRef: MatDialogRef<DialogTypeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private typeService: TypeService,
    private technicianService: TechnicianService,
    private notification: NotificationService
  ) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  async onSave(): Promise<void> {
    const technicianId = await this.technicianService.getTechnicianProfileByUsername(localStorage.getItem('username'));
    if (technicianId) {
      this.data.technicianId = technicianId;
      this.typeService.addType(this.data).subscribe(
        (result) => {
          this.dialogRef.close(result);
          this.notification.showNotification("Service successfully saved");
        },
        (error) => {
          console.error('Error adding/updating type:', error);
        }
      );
    }
  }
}
