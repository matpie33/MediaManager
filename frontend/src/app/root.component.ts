import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MenuItems} from "./menu-items";
import {LoginConstants} from "./login/login-enums";
import {RestHandlerService} from "./rest-handler.service";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./global-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class RootComponent implements OnInit{
  menuItems: (string | MenuItems) [] = Object.values(MenuItems);
  username: string | null;
  menuToPermissionMap : Map<string, Array<MenuItems> | MenuItems> = new Map<string, Array<MenuItems> | MenuItems>();
  userAccessibleMenu: Set<MenuItems> = new Set<MenuItems>();

  constructor(private restService: RestHandlerService) {
    this.username = sessionStorage.getItem(LoginConstants.USERNAME);
    this.initializeMenuPermissions();
  }


  initializeMenuPermissions (){
    this.userAccessibleMenu.add(MenuItems.NEWS);
    this.userAccessibleMenu.add(MenuItems.SEARCH_CONNECTIONS);
    let defaultMenu: Array<MenuItems> = [
       MenuItems.TICKETS_MANAGEMENT, MenuItems.PROFILE
    ];

    this.menuToPermissionMap.set("USER_ACTIVITIES", defaultMenu);
    this.menuToPermissionMap.set("ADD_TRAVEL_CONNECTION",MenuItems.ADD_CONNECTION);

  }


  logout() {
    sessionStorage.clear();
    window.location.reload();
  }

  ngOnInit(): void {
    this.restService.getUserPermissions(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!))
      .subscribe(response=>{
        let permissions = response.permissions;
        this.menuToPermissionMap.forEach((menu: MenuItems | Array<MenuItems>, permission: string)=>{
          if (permissions.includes(permission)){

            if (Array.isArray(menu)){
              (menu as Array<MenuItems>).forEach(m=>{
                this.userAccessibleMenu.add(m);
              });

            }
            else{
              this.userAccessibleMenu.add(menu as MenuItems);
            }
          }
        })
      });
  }


}
