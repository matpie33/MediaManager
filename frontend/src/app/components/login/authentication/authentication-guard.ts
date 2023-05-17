import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import {Observable} from 'rxjs';
import {LoginConstants} from "../data/login-enums";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard  {
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
    if (sessionStorage.getItem(LoginConstants.USER_ID.toString())) {
      status = true;
    }
    return status;
  }
}

