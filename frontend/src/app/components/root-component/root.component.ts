import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MenuItems} from "../../constants/menu-items";
import {LoginConstants} from "../login/data/login-enums";
import {RestClientService} from "../../services/rest-client.service";
import {PermissionTypes} from "../../constants/permission-types";
import {PermissionsService} from "../../services/permissions.service";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['../../global-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class RootComponent implements OnInit{
  menuItems: (string | MenuItems) [] = Object.values(MenuItems);

  userAccessibleMenu: Set<MenuItems> = new Set<MenuItems>();

  constructor(private permissionService: PermissionsService) {

    this.initializeMenuPermissions();
  }

  initializeMenuPermissions (){
    this.userAccessibleMenu.add(MenuItems.NEWS);
    this.userAccessibleMenu.add(MenuItems.SEARCH_CONNECTIONS);


  }



  ngOnInit(): void {
    this.permissionService.getUserMenus().subscribe(menuItems=>{
      for (let menuItem of menuItems){
        this.userAccessibleMenu.add(menuItem);
      }
    })
  }


}
