import { Component } from '@angular/core';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  message: string | null = null;
  constructor(private notification: NotificationService){}

  ngOnInit() {
    this.notification.notifications$.subscribe(message => {
      this.message = message;
      setTimeout(() => this.message = null, 3000);
    });
  }
}
