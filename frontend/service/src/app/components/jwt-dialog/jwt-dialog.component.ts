import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-jwt-dialog',
  templateUrl: './jwt-dialog.component.html',
  styleUrl: './jwt-dialog.component.css'
})
export class JwtDialogComponent {

  constructor(public dialogRef: MatDialogRef<JwtDialogComponent>) {}

  goToLogin(): void {
    this.dialogRef.close();
  }
}
