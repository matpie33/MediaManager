import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import {Observable, map, catchError, of} from 'rxjs';
import {LoginConstants} from "../data/login-enums";
import {RestClientService} from "../../../services/rest-client.service";
import {PermissionsService} from "../../../services/permissions.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuardAdmin  {
  constructor(private router: Router, private restHandler: RestClientService, private permissionService: PermissionsService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    let path = route.url.at(0)!.path;
    return this.permissionService.getUserMenus().pipe(map(menus=>{
      for (let menu of menus){
        if (menu.toString() === path){
          return true;
        }

      }
      this.router.navigate([LoginConstants.ACCESS_DENIED.toString()]);
      return false;
    }), catchError(()=>{
      let returnUrl = LoginConstants.RETURN_URL;
      this.router.navigate([LoginConstants.LOGIN_URL.toString()],
        { queryParams: { [returnUrl]: state.url }}).catch(error=>console.log(error));
      return of(false);
    }));


    }

}

