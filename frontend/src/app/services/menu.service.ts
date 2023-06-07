import { Injectable } from '@angular/core';
import {MenuItems} from "../constants/menu-items";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  menuToIconMap = new Map<string, string>();

  constructor() {
    this.initializeMenuToIconMap();
  }

  private initializeMenuToIconMap() {
    this.menuToIconMap.set(MenuItems.SEARCH_CONNECTIONS, "search");
    this.menuToIconMap.set(MenuItems.ADD_CONNECTION, "add_circle");
    this.menuToIconMap.set(MenuItems.SCAN_QR_CODE, "videocam");
    this.menuToIconMap.set(MenuItems.NEWS, "library_books");
    this.menuToIconMap.set(MenuItems.PROFILE, "edit");
    this.menuToIconMap.set(MenuItems.MANAGE_USERS, "contact_mail");
    this.menuToIconMap.set(MenuItems.TICKETS_MANAGEMENT, "list");
    this.menuToIconMap.set(MenuItems.DELAYS, "access_time");
    this.menuToIconMap.set(MenuItems.ADD_DELAY, "alarm_add");
  }

  getIcon (menuItem: MenuItems){
    return this.menuToIconMap.get(menuItem);
  }

}
