import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notifications = new Subject<string>();
  notifications$ = this.notifications.asObservable();
  constructor() { }

  showNotification(message: string) {
    this.notifications.next(message);
  }
}
