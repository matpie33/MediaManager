import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MenuItems} from "../../../constants/menu-items";
import {LoginConstants} from "../../login/data/login-enums";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Output() public sidenavToggle = new EventEmitter();
  @Input() userAccessibleMenu!: Set<MenuItems>;
  menuToIconMap = new Map<string, string>();
  username: string | null;

  constructor() {
    this.username = sessionStorage.getItem(LoginConstants.USERNAME);
    this.menuToIconMap.set(MenuItems.SEARCH_CONNECTIONS, "search");
    this.menuToIconMap.set(MenuItems.ADD_CONNECTION, "add_circle");
    this.menuToIconMap.set(MenuItems.SCAN_QR_CODE, "videocam");
    this.menuToIconMap.set(MenuItems.NEWS, "library_books");
    this.menuToIconMap.set(MenuItems.PROFILE, "edit");
    this.menuToIconMap.set(MenuItems.MANAGE_USERS, "contact_mail");
    this.menuToIconMap.set(MenuItems.TICKETS_MANAGEMENT, "list");
  }

  getIcon (menu: string){
    return this.menuToIconMap.get(menu);
  }


  onToggleSidenav() {
  this.sidenavToggle.emit();
  }

  logout() {
    sessionStorage.clear();
    window.location.reload();
  }



}
