import {Component, Input} from '@angular/core';
import {TicketData} from "../data/ticket-data";
import {RestHandlerService} from "../../../services/rest-handler.service";
import {LoginConstants} from "../../login/data/login-enums";
import {DATE_FORMAT} from "../../../constants/date-formats";

@Component({
  selector: 'app-ticket-summary',
  templateUrl: './ticket-summary.component.html',
  styleUrls: ['./ticket-summary.component.css']
})
export class TicketSummaryComponent {
  @Input() ticketData!: TicketData;
  submitButtonEnabled: boolean = false;
  buyRequestSent: boolean = false;
  statusColor: string = "";
  paymentStatusMessage = "";
  dateFormat = DATE_FORMAT;

  constructor(private restHandler: RestHandlerService) {
  }

  confirmAndBuy() {
    this.buyRequestSent = true;
    this.paymentStatusMessage="Please wait for response";
    this.statusColor="blue";

    let userId = Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!);
    this.restHandler.assignTicketToUser(userId, this.ticketData).subscribe(isSuccess=> {
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
