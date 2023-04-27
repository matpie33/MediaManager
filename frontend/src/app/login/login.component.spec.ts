import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {RestHandlerService} from "../rest-handler.service";
import {RouterTestingModule} from "@angular/router/testing";
import {ReactiveFormsModule} from "@angular/forms";
import {By} from "@angular/platform-browser";
import {of, throwError} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {LoginConstants} from "./login-enums";
import {Component} from "@angular/core";


describe('LoginComponent', () => {
  let loginComponent: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let serviceSpy: any;
  beforeEach(async () => {
    serviceSpy = jasmine.createSpyObj(RestHandlerService.name, [RestHandlerService.prototype.loginUser.name]);
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [RouterTestingModule, ReactiveFormsModule],
      providers: [{
        provide: RestHandlerService, useValue: serviceSpy
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    loginComponent = fixture.componentInstance;

  });

  it('should login user and navigate and set session parameters', () => {
    loginComponent.loginForm.controls["username"].setValue("user123");
    loginComponent.loginForm.controls["password"].setValue("pass11");
    let activatedRoute = TestBed.inject(ActivatedRoute);
    activatedRoute.snapshot.queryParams[LoginConstants.RETURN_URL]="ret";
    let router = TestBed.inject(Router);
    spyOn(router, "navigateByUrl").and.returnValue(new Promise(()=>1));

    fixture.detectChanges();
    serviceSpy.loginUser.and.returnValue(of({
      id: 1,
      permissions: []
    }));
    let loginForm = fixture.debugElement.query(By.css("#loginForm"));

    loginForm.triggerEventHandler('submit');

    expect(loginComponent.loginMessage).toBe("Successfully logged in. Redirecting...");
    expect(router.navigateByUrl).toHaveBeenCalledWith("ret");
    expect(sessionStorage.getItem(LoginConstants.USERNAME)).toBe("user123");
    expect(sessionStorage.getItem(LoginConstants.USER_ID)).toBe("1");
    sessionStorage.clear();
  });

  it('should fail and set message', () => {
    serviceSpy.loginUser.and.returnValue(throwError(()=>new Error("error")));
    fixture.detectChanges();
    let loginForm = fixture.debugElement.query(By.css("#loginForm"));
    loginForm.triggerEventHandler('submit');
    expect(loginComponent.loginMessage).toBe("Failed to log in.");
  });

});
