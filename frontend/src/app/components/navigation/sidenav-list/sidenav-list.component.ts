import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MenuItems} from "../../../constants/menu-items";

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent {
  @Output() sidenavClose = new EventEmitter();
  @Input() userAccessibleMenu!: Set<MenuItems>;
  constructor() { }
  ngOnInit() {
  }
  public onSidenavClose = () => {
    this.sidenavClose.emit();
  }
}
