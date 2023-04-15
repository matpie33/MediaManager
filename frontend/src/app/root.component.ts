import {Component, ViewEncapsulation} from '@angular/core';
import {MenuItems} from "./menu-items";
import {UserManagementService} from "./user-management-service";
import {LoginConstants} from "./login/login-enums";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./global-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class RootComponent{
  menuItems: (string | MenuItems) [] = Object.values(MenuItems);
  username: string | null;

  constructor(private userManagementService: UserManagementService) {
    this.userManagementService.username.subscribe(value=>this.username = value);
    this.username = sessionStorage.getItem(LoginConstants.USERNAME);
  }


  logout() {
    sessionStorage.clear();
    window.location.reload();
  }
}
