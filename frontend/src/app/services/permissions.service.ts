import { Injectable } from '@angular/core';
import {LoginConstants} from "../components/login/data/login-enums";
import {MenuItems} from "../constants/menu-items";
import {RestClientService} from "./rest-client.service";
import {PermissionTypes} from "../constants/permission-types";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {
  menuToPermissionMap : Map<string, Array<MenuItems> | MenuItems> = new Map<string, Array<MenuItems> | MenuItems>();


  constructor(private restService: RestClientService) {
    let defaultMenu: Array<MenuItems> = [
      MenuItems.TICKETS_MANAGEMENT, MenuItems.PROFILE
    ];
    this.menuToPermissionMap.set(PermissionTypes.USER_ACTIVITIES, defaultMenu);
    this.menuToPermissionMap.set(PermissionTypes.ADD_TRAVEL_CONNECTION,MenuItems.ADD_CONNECTION);
    this.menuToPermissionMap.set(PermissionTypes.CREATE_USER_WITH_ROLE, MenuItems.MANAGE_USERS);
    this.menuToPermissionMap.set(PermissionTypes.CREATE_USER_WITH_ROLE, MenuItems.SCAN_QR_CODE);
  }

  getUserMenus () : Observable<Set<MenuItems>>{

    return this.restService.getUserPermissions(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!))
      .pipe(map(response=>{
      let userAccessibleMenu: Set<MenuItems> = new Set<MenuItems>();
        this.menuToPermissionMap.forEach((menu: MenuItems | Array<MenuItems>, permission: string)=>{
          let permissions = response.permissions;
          if (permissions.includes(permission)){
            if (Array.isArray(menu)){
              (menu as Array<MenuItems>).forEach(m=>{
                userAccessibleMenu.add(m);
              });

            }
            else{
              userAccessibleMenu.add(menu as MenuItems);
            }
          }

      });
      return userAccessibleMenu;
    }));
  }

}
