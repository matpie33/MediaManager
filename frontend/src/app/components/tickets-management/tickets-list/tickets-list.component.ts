import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../../services/rest-client.service";
import {UserTicket} from "../data/ticket-of-user";
import {LoginConstants} from "../../login/data/login-enums";
import {DATE_FORMAT} from "../../../constants/date-formats";
import {PersonalData} from "../../login/data/login-data";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.css']
})
export class TicketsListComponent implements OnInit{
 tickets: Array<UserTicket> = [];
  dateFormat = DATE_FORMAT;
  dataLoaded = false;
  personalData!: PersonalData;

 constructor(private restHandler: RestClientService) {
 }

  ngOnInit(): void {
    this.restHandler.getTicketsOfUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe(tickets => {
      this.tickets = tickets;
      this.dataLoaded = true;
    });
    this.restHandler.getUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!))
      .subscribe(personalData=>{
      this.personalData = personalData;
    })
  }

  openTicket(ticket: UserTicket){
    const options ={ responseType: 'blob'};
    let ticketData = {
      firstName: this.personalData.firstName,
      lastName: this.personalData.lastName,
      email: this.personalData.email,
      travelDate: new DatePipe("en").transform(ticket.travelDate.toString(), DATE_FORMAT)!,
      fromStation: ticket.connection.fromStation,
      toStation: ticket.connection.toStation,
      ticketType: ticket.ticketType,
      trainName: ticket.trainName,
      trainTime: ticket.connection.time
    }
    this.restHandler.getTicketAsPdf(ticketData, options).subscribe(bytes=>{
      let blob = new Blob([bytes], {type: 'application/pdf'});
      const fileUrl = URL.createObjectURL(blob);
      window.open(fileUrl, '_blank');
   })
  }

}
