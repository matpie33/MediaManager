import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../../services/rest-client.service";
import {UserTicket} from "../data/ticket-of-user";
import {LoginConstants} from "../../login/data/login-enums";
import {DATE_FORMAT} from "../../../constants/date-formats";

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.css']
})
export class TicketsListComponent implements OnInit{
 tickets: Array<UserTicket> = [];
  dateFormat = DATE_FORMAT;
  dataLoaded = false;

 constructor(private restHandler: RestClientService) {
 }

  ngOnInit(): void {
    this.restHandler.getTicketsOfUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe(tickets => {
      this.tickets = tickets;
      this.dataLoaded = true;
    })
  }
}
