import {Component, Input} from '@angular/core';
import {TicketData} from "../data/ticket-data";
import {RestClientService} from "../../../services/rest-client.service";
import {LoginConstants} from "../../login/data/login-enums";
import {DATE_FORMAT} from "../../../constants/date-formats";
import {ViewWithStatus} from "../../common/view-with-status";

@Component({
  selector: 'app-ticket-summary',
  templateUrl: './ticket-summary.component.html',
  styleUrls: ['./ticket-summary.component.css']
})
export class TicketSummaryComponent extends ViewWithStatus{
  @Input() ticketData!: TicketData;
  submitButtonEnabled: boolean = false;
  buyRequestSent: boolean = false;
  dateFormat = DATE_FORMAT;

  constructor(private restHandler: RestClientService) {
    super();
  }

  confirmAndBuy() {
    this.buyRequestSent = true;
    this.showInfoMessage("Please wait for response");

    let userId = Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!);
    this.restHandler.assignTicketToUser(userId, this.ticketData).subscribe({
      complete: ()=>this.showSuccessMessage("Payment succesfull"),
      error: ()=>this.showErrorMessage("Payment failed")
    });
    this.submitButtonEnabled = true;
  }


}
