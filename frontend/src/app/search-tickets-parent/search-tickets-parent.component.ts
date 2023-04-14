import {Component} from '@angular/core';
import {TicketData} from "./data/ticket-data";

@Component({
  selector: 'app-search-tickets-parent',
  templateUrl: './search-tickets-parent.component.html',
  styleUrls: ['./search-tickets-parent.component.css']
})
export class SearchTicketsParentComponent {
  isSubmitted!:boolean;
  ticketData!: TicketData;

   onSubmit (data: TicketData){
     this.isSubmitted = true;
     this.ticketData = data;
     console.log(data);
   }

}
