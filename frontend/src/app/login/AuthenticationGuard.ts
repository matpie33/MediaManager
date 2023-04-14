import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {MenuItems} from "../menu-items";
import {LoginConstants} from "./login-enums";
import {LoginComponent} from "./login.component";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (this.isLoggedIn()) {
      return true;
    }
    let returnUrl = LoginConstants.RETURN_URL;
    this.router.navigate([LoginConstants.LOGIN_URL.toString()], { queryParams: { [returnUrl]: state.url }}).catch(error=>console.log(error));
    return false;
    }

  public isLoggedIn(): boolean {
    let status = false;
    if (localStorage.getItem(LoginConstants.USER_ID.toString())) {
      status = true;
    }
    return status;
  }
}

