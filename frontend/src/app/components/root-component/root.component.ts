import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MenuItems} from "../../constants/menu-items";
import {PermissionsService} from "../../services/permissions.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['../../global-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class RootComponent implements OnInit{
  userAccessibleMenu: Set<MenuItems> = new Set<MenuItems>();
  loadingData = true;
  routerActivated = false;

  constructor(private permissionService: PermissionsService, private router: Router) {
    this.initializeMenuPermissions();
    this.routerActivated = router.navigated;
  }

  initializeMenuPermissions (){
    this.userAccessibleMenu.add(MenuItems.NEWS);
    this.userAccessibleMenu.add(MenuItems.SEARCH_CONNECTIONS);
  }

  isLoadingData(){
    return this.permissionService.loadingData;
  }

  ngOnInit(): void {
    this.permissionService.getUserMenus().subscribe({
      next:
        menuItems=>{
          for (let menuItem of menuItems){
            this.userAccessibleMenu.add(menuItem);
          }
        },
      complete: ()=>this.loadingData=false

    })
  }

  activated() {
    this.routerActivated = true;
  }
}
