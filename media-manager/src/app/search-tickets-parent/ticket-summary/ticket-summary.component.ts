import {Component, Input} from '@angular/core';
import {TicketData} from "../data/ticket-data";
import {BuyTicketService} from "../buy-ticket.service";

@Component({
  selector: 'app-ticket-summary',
  templateUrl: './ticket-summary.component.html',
  styleUrls: ['./ticket-summary.component.css'],
  providers: [BuyTicketService]
})
export class TicketSummaryComponent {
  @Input() ticketData!: TicketData;
  submitButtonEnabled: boolean = false;
  buyRequestSent: boolean = false;
  statusColor: string = "";
  paymentStatusMessage = "";

  constructor(private buyTicketService:BuyTicketService) {
  }

  confirmAndBuy() {
    this.buyRequestSent = true;
    this.paymentStatusMessage="Please wait for response";
    this.statusColor="blue";

    this.buyTicketService.buyTicket(this.ticketData).subscribe(isSuccess=> {
      if (isSuccess){
        this.statusColor="rgb(98,251,109)";
        this.paymentStatusMessage = "Payment successfull";
        alert("you bought the ticket");
      }
      else{
        this.statusColor="red";
        this.paymentStatusMessage = "Payment failed";
        alert("Payment failed");

      }
    });
    this.submitButtonEnabled = true;
  }
}
