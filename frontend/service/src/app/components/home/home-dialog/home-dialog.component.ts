import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-home-dialog',
  templateUrl: './home-dialog.component.html',
  styleUrl: './home-dialog.component.css'
})
export class HomeDialogComponent {
  constructor(public dialogRef: MatDialogRef<HomeDialogComponent>) {}

  onConfirm(): void {
    this.dialogRef.close();
  }
}
