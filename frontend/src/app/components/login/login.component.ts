import {Component, EventEmitter, Output} from '@angular/core';
import {Form, FormControl, FormGroup, Validators} from "@angular/forms";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {LoginConstants} from "./data/login-enums";
import * as crypto from 'crypto-js'
import {RestHandlerService} from "../../services/rest-handler.service";
import {LoginResponse} from "./data/login-data";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup<any>({
    username: new FormControl(""),
    password: new FormControl("")
  });

  loginMessageColor: string = "blue";
  loginMessage: string = "";
  registerMessageColor: string = "blue";
  registerMessage: string = "";
  registerForm: FormGroup = this.formBuilder.group({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    email: "",
  });
  showRegisterForm = false;
  actionDelay = 3;
  encryptionKey = "R0U8T7MFBAXfJe6DpHeM";

  constructor(private restHandler: RestHandlerService, private formBuilder: FormBuilder, private router:Router, private route:ActivatedRoute) {
  }

  onSubmitLogin (){
    this.loginMessageColor = "blue";
    this.loginMessage = "Please wait... logging in..."
    this.restHandler.loginUser({
      userName: this.loginForm.controls["username"].value,
      password: crypto.SHA512(this.loginForm.controls["password"].value).toString(),
    }).subscribe({
      next: this.handleLoginSuccess.bind(this),
      error: this.handleLoginError.bind(this)
  });
  }

  handleLoginSuccess(response: LoginResponse){
    if (response){
      let queryParam = this.route.snapshot.queryParams[LoginConstants.RETURN_URL];
      let username = this.loginForm.controls["username"].value;
      sessionStorage.setItem(LoginConstants.USERNAME, username);
      sessionStorage.setItem(LoginConstants.USER_ID, response.id.toString());
      this.loginMessage = "Successfully logged in. Redirecting...";
      this.loginMessageColor = "green";
      this.router.navigateByUrl(queryParam).catch(error=>console.log(error)).then(success=>{

        window.location.reload();
      })
    }
  }

  handleLoginError(){
    this.loginMessage = "Failed to log in.";
    this.loginMessageColor = "red";
  }

  onSubmitRegister() {
    this.registerMessageColor = "blue";
    this.registerMessage = "Please wait... registering..."
    this.restHandler.registerUser({
      userCredentials: {
        userName: this.registerForm.controls["username"].value,
        password: crypto.SHA512(this.registerForm.controls["password"].value).toString(),
      },
      personalData: {
        firstName: this.registerForm.controls["firstName"].value,
        lastName: this.registerForm.controls["lastName"].value,
        email: this.registerForm.controls["email"].value
      }
    }).subscribe({
      next: this.handleRegisterSuccess.bind(this),
      error: this.handleRegisterError.bind(this)
    });
  }

  handleRegisterSuccess (){
      this.registerMessage = `Successfully registered. Redirecting to login page in ${this.actionDelay} seconds...`;
      this.registerMessageColor = "green";
      setTimeout(()=> {
        this.showRegisterForm = false;
        this.registerMessage = "";
      }, this.actionDelay*1000);
  }

  handleRegisterError (){
    this.registerMessage = "Username already exists.";
    this.registerMessageColor = "red";
  }

  openRegister() {
    this.showRegisterForm = true;
  }

  goToLogin() {
    this.showRegisterForm = false;
  }
}
