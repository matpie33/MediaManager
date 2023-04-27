import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, map, catchError, of} from 'rxjs';
import {MenuItems} from "../../../constants/menu-items";
import {LoginConstants} from "../data/login-enums";
import {LoginComponent} from "../login.component";
import {RestHandlerService} from "../../../services/rest-handler.service";
import {LoginResponse} from "../data/login-data";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuardAdmin implements CanActivate {
  constructor(private router: Router, private restHandler: RestHandlerService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    return this.restHandler.getUserPermissions(Number.parseInt(
      sessionStorage.getItem(LoginConstants.USER_ID)!)).pipe(map((r: LoginResponse)=>{
        if (r.permissions.includes("ADD_TRAVEL_CONNECTION")){
          return true;
        }
        else{
          this.router.navigate([LoginConstants.ACCESS_DENIED.toString()]);
          return false;
        }
    }), catchError((er, caught)=>{
      let returnUrl = LoginConstants.RETURN_URL;
      this.router.navigate([LoginConstants.LOGIN_URL.toString()],
        { queryParams: { [returnUrl]: state.url }}).catch(error=>console.log(error));
      return of(false);
    }));


    }

}

