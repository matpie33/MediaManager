import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {LoginConstants} from "./data/login-enums";
import * as crypto from 'crypto-js'
import {RestHandlerService} from "../../services/rest-handler.service";
import {LoginResponse} from "./data/login-data";
import {ViewWithStatus} from "../common/view-with-status";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends ViewWithStatus{
  loginForm: FormGroup = new FormGroup<any>({
    username: new FormControl(""),
    password: new FormControl("")
  });

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
    super();
  }

  onSubmitLogin (){
    this.showSuccessMessage("Please wait... logging in...");
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
      this.showSuccessMessage("Successfully logged in. Redirecting...");
      this.router.navigateByUrl(queryParam).catch(error=>console.log(error)).then(()=>{

        window.location.reload();
        this.showStatus = false;
      })
    }
  }

  handleLoginError(){
    this.showErrorMessage("Failed to log in.");
  }

  onSubmitRegister() {
    this.showSuccessMessage("Please wait... registering...");
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
    this.showSuccessMessage(`Successfully registered. Redirecting to login page in ${this.actionDelay} seconds...`);
    this.hideStatusAfterDelay(()=>this.showRegisterForm=false, this.actionDelay);
  }

  handleRegisterError (){
    this.showErrorMessage("Username already exists.");
  }

  openRegister() {
    this.showRegisterForm = true;
    this.showStatus = false;
  }

  goToLogin() {
    this.showRegisterForm = false;
    this.showStatus = false;
  }
}
