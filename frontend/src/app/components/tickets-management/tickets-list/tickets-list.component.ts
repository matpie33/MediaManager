import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../../services/rest-client.service";
import {UserTicket} from "../data/ticket-of-user";
import {LoginConstants} from "../../login/data/login-enums";
import {DATE_FORMAT} from "../../../constants/date-formats";
import {PersonalData} from "../../login/data/login-data";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.css']
})
export class TicketsListComponent implements OnInit{
 tickets: Array<UserTicket> = [];
  dateFormat = DATE_FORMAT;
  loadingData = true;
  personalData!: PersonalData;

 constructor(private restHandler: RestClientService) {
 }

  ngOnInit(): void {
    let ticketsObservable = this.restHandler.getTicketsOfUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!));
    let userDataObservable = this.restHandler.getUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!));
    let observables = [ticketsObservable, userDataObservable];
    forkJoin(observables).subscribe(result=>{
      this.tickets = result[0] as UserTicket[];
      this.personalData = result[1] as PersonalData;
      this.loadingData = false;
    });
  }

  openTicket(ticket: UserTicket){
    const options ={ responseType: 'blob'};
    ticket.isLoading = true;

    this.restHandler.getTicketAsPdf(ticket.id, options).subscribe(bytes=>{
      let blob = new Blob([bytes], {type: 'application/pdf'});
      const fileUrl = URL.createObjectURL(blob);
      window.open(fileUrl, '_blank');
      ticket.isLoading = false;
    })
  }

}
