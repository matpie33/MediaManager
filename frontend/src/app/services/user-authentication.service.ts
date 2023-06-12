import { Injectable } from '@angular/core';
import * as crypto from "crypto-js";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {RestClientService} from "./rest-client.service";
import {EMPTY, empty} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserAuthenticationService {

  constructor(private formBuilder: FormBuilder, private restService: RestClientService) {
  }

  registerForm: FormGroup = this.formBuilder.group({
    username: "",
    password: "",
    passwordConfirm: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    notificationTypes: this.formBuilder.group({
      SMS: true,
      EMAIL: true
    })
  });

  loginForm: FormGroup = new FormGroup<any>({
    username: new FormControl(""),
    password: new FormControl("")
  });


  private generateHash(text: string){
    return crypto.SHA512(text).toString();
  }

  register ( rolesSet: Array<string>){
    return this.restService.registerUser({
      roles: rolesSet,
      userCredentials: {
        userName: this.registerForm.controls["username"].value,
        password: this.generateHash(this.registerForm.controls["password"].value),
      },
      personalData: {
        firstName: this.registerForm.controls["firstName"].value,
        lastName: this.registerForm.controls["lastName"].value,
        email: this.registerForm.controls["email"].value,
        phoneNumber: this.registerForm.controls["phoneNumber"].value,
        acceptedNotificationTypes: this.getSelectedNotifications()
      }
    })
  }

  login (){
    return this.restService.loginUser({
      userName: this.loginForm.controls["username"].value,
      password: this.generateHash(this.loginForm.controls["password"].value),
    });
  }

  getUserName (){
    return this.loginForm.value["username"];
  }

  passwordsNotMatching (){
    return this.registerForm.value["password"] !== this.registerForm.value["passwordConfirm"];
  }


  private getSelectedNotifications() {
    let controls = this.registerForm.get("notificationTypes")!.value;
    let selectedNotifications = [];
    for (let controlName in controls){
      if (controls[controlName] == true){
        selectedNotifications.push(controlName);
      }
    }
    return selectedNotifications;
  }
}
