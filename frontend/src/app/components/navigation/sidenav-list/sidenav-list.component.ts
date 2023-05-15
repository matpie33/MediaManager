import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MenuItems} from "../../../constants/menu-items";
import {MenuService} from "../../../services/menu.service";

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent {
  @Output() sidenavClose = new EventEmitter();
  @Input() userAccessibleMenu!: Set<MenuItems>;
  constructor(private menuService: MenuService) { }
  public onSidenavClose = () => {
    this.sidenavClose.emit();
  }

  getIcon (menu: MenuItems){
    return this.menuService.getIcon(menu);
  }

}
