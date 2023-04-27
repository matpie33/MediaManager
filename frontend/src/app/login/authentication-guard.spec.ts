import { TestBed } from '@angular/core/testing';

import {AuthenticationGuard} from "./authentication-guard";
import {ActivatedRouteSnapshot, Router, RouterState, RouterStateSnapshot} from "@angular/router";
import {LoginConstants} from "./login-enums";

describe('AuthenticationGuard', () => {

  let routeSnapshot: ActivatedRouteSnapshot;
  let routerStateSnapshot: any;
  let authenticationGuard: AuthenticationGuard;
  let router: any;
  let routerNavigateSpy: any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({});

    router = jasmine.createSpyObj(Router.name, [Router.prototype.navigate.name]);
    authenticationGuard = new AuthenticationGuard(router);
    let routerStateSpy:any = jasmine.createSpy(RouterState.name);
    router.routerState = routerStateSpy;
    routerStateSnapshot = jasmine.createSpy(RouterStateSnapshot.name);
    routerStateSnapshot.url = "testUrl";
    routerStateSpy.routerStateSnapshot = routerStateSnapshot;
    routerNavigateSpy = router.navigate.and.returnValue(new Promise<boolean>((resolve)=>{
      resolve(true);
    }));
    sessionStorage.clear();
  });

  it('should be able to enter when user id is set', () => {
    sessionStorage.setItem(LoginConstants.USER_ID, "1");
    let canActivate = authenticationGuard.canActivate(routeSnapshot, routerStateSnapshot);
    expect(canActivate).toBeTruthy();
  });

  it('should not be able to enter', () => {
    let canActivate = authenticationGuard.canActivate(routeSnapshot, routerStateSnapshot);
    expect(canActivate).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith([LoginConstants.LOGIN_URL.toString()], {queryParams: {
      [LoginConstants.RETURN_URL]: routerStateSnapshot.url
      }});
  });
});
