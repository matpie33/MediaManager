import {Component, ViewEncapsulation} from '@angular/core';
import {MenuItems} from "./menu-items";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./global-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class RootComponent {
  menuItems: (string | MenuItems) [] = Object.values(MenuItems);


  func() :void {
  }
}
