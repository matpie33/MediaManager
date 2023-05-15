import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MenuItems} from "../../../constants/menu-items";
import {LoginConstants} from "../../login/data/login-enums";
import {MenuService} from "../../../services/menu.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Output() public sidenavToggle = new EventEmitter();
  @Input() userAccessibleMenu!: Set<MenuItems>;

  username: string | null;

  constructor(private menuService: MenuService) {
    this.username = sessionStorage.getItem(LoginConstants.USERNAME);

  }

  getIcon (menu: MenuItems){
    return this.menuService.getIcon(menu);
  }


  onToggleSidenav() {
  this.sidenavToggle.emit();
  }

  logout() {
    sessionStorage.clear();
    window.location.reload();
  }



}
