import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {LoginService} from "../login/login.service";

@Component({
  selector: 'app-tickets-management',
  templateUrl: './tickets-management.component.html',
  styleUrls: ['./tickets-management.component.css']
})
export class TicketsManagementComponent {

  constructor(private loginService: LoginService) {
    this.isLoggedIn = loginService.isLoggedIn;
  }

  isLoggedIn = false;

  logIn(){
    this.isLoggedIn = true;
  }
}
