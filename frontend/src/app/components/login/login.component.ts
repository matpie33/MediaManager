import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoginConstants} from "./data/login-enums";
import {LoginResponse} from "./data/login-data";
import {ViewWithStatus} from "../common/view-with-status";
import {Role} from "../../constants/role";
import {UserAuthenticationService} from "../../services/user-authentication.service";
import {NotificationType} from "../../constants/notification-type";
import {resetParseTemplateAsSourceFileForTest} from "@angular/compiler-cli/src/ngtsc/typecheck/diagnostics";
import {KeyValue} from "@angular/common";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent extends ViewWithStatus{

  showRegisterForm = false;
  actionDelay = 3;
  registerForm = this.userAuthenticationService.registerForm;
  loginForm = this.userAuthenticationService.loginForm;

  constructor(private router:Router, private route:ActivatedRoute, private userAuthenticationService: UserAuthenticationService) {
    super();
  }

  onSubmitLogin (){
    this.showSuccessMessage("Please wait... logging in...");
    this.userAuthenticationService.login().subscribe({
      next: this.handleLoginSuccess.bind(this),
      error: this.handleLoginError.bind(this)
  });
  }

  handleLoginSuccess(response: LoginResponse){
    if (response){
      let queryParam = this.route.snapshot.queryParams[LoginConstants.RETURN_URL];
      let username = this.userAuthenticationService.getUserName();
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
    this.showInfoMessage("Please wait... registering...");
    let rolesSet = [Role.USER];
    this.userAuthenticationService.register(rolesSet).subscribe({
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

  getNotificationTypeForDisplay(notif: KeyValue<unknown, unknown>) {
    return notif.key as string;
  }
}
