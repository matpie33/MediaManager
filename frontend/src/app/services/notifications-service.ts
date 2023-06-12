import { Injectable } from '@angular/core';
import {MenuItems} from "../constants/menu-items";
import {NotificationType} from "../constants/notification-type";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  notificationTypes = NotificationType;

  constructor() {
  }

  getSelectedNotifications(form: FormGroup) {
    return Array.from(Object.keys(this.notificationTypes))
      .filter(notificationType=>form.get("notificationTypes")!.get(notificationType)?.value == true);

  }


}
