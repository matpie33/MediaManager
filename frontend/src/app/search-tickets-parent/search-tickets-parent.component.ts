import {Component} from '@angular/core';
import {TicketData} from "./data/ticket-data";
import {LoginService} from "../login/login.service";

@Component({
  selector: 'app-search-tickets-parent',
  templateUrl: './search-tickets-parent.component.html',
  styleUrls: ['./search-tickets-parent.component.css']
})
export class SearchTicketsParentComponent {
  isSubmitted!:boolean;
  ticketData!: TicketData;
  isLoggedIn = false;
  constructor(private loginService: LoginService) {
    this.isLoggedIn = loginService.isLoggedIn;
  }

   onSubmit (data: TicketData){
     this.isSubmitted = true;
     this.ticketData = data;
     console.log(data);
   }

  logIn() {
    this.isLoggedIn = true;
  }
}
