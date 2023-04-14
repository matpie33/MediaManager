import {Component, EventEmitter, Output} from '@angular/core';
import {Form, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {LoginConstants} from "./login-enums";

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

  constructor(private loginService:LoginService, private formBuilder: FormBuilder, private router:Router, private route:ActivatedRoute) {
  }

  onSubmitLogin (){

    this.loginMessageColor = "blue";
    this.loginMessage = "Please wait... logging in..."
    this.loginService.login({
      username: this.loginForm.controls["username"].value,
      password: this.loginForm.controls["password"].value,
    }).subscribe(result=>{
      if (result){
        let queryParam = this.route.snapshot.queryParams[LoginConstants.RETURN_URL];
        localStorage.setItem(LoginConstants.USER_ID, "1");
        this.loginMessage = "Successfully logged in. Redirecting...";
        this.loginMessageColor = "green";
        this.router.navigateByUrl(queryParam).catch(error=>console.log(error));
      }
      else{
        this.loginMessage = "Failed to log in.";
        this.loginMessageColor = "red";
      }

    });
  }

  onSubmitRegister() {
    this.registerMessageColor = "blue";
    this.registerMessage = "Please wait... registering..."
    this.loginService.register({
      username: this.registerForm.controls["username"].value,
      password: this.registerForm.controls["password"].value,
      firstName: this.registerForm.controls["firstName"].value,
      lastName: this.registerForm.controls["lastName"].value,
      email: this.registerForm.controls["email"].value,
    }).subscribe(result=> {
      if (result) {
        this.registerMessage = `Successfully registered. Redirecting to login page in ${this.actionDelay} seconds...`;
        this.registerMessageColor = "green";
        setTimeout(()=> {
          this.showRegisterForm = false;
          this.registerMessage = "";
        }, this.actionDelay*1000);
      } else {
        this.registerMessage = "Username already exists.";
        this.registerMessageColor = "red";
      }
    });
  }

  openRegister() {
    this.showRegisterForm = true;
  }

  goToLogin() {
    this.showRegisterForm = false;
  }
}
