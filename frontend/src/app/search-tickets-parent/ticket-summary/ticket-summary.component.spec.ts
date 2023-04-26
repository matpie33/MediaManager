import {TicketSummaryComponent} from './ticket-summary.component';
import {of} from "rxjs";
import {LoginConstants} from "../../login/login-enums";
import {TicketType} from "../data/ticket-data";

describe('TicketSummaryComponent', () => {
  let ticketSummaryComponent: TicketSummaryComponent;
  let restHandlerService: any;

  beforeEach(async () => {

    restHandlerService = jasmine.createSpyObj("RestHandlerService", ["assignTicketToUser"]);
    ticketSummaryComponent = new TicketSummaryComponent(restHandlerService);
    sessionStorage.clear();

  });

  it('should call rest handler when buying ticket', () => {
    ticketSummaryComponent.ticketData = {
      connectionId: 15,
      fromStation: "",
      ticketType: TicketType.FULL_FARE,
      toStation: "",
      travelDate: ""
    };
    sessionStorage.setItem(LoginConstants.USER_ID, "1");
    restHandlerService.assignTicketToUser.and.returnValue(of(true));
    ticketSummaryComponent.confirmAndBuy();
    expect(ticketSummaryComponent.paymentStatusMessage = "Payment successfull");
    expect(restHandlerService.assignTicketToUser).toHaveBeenCalledWith(1, ticketSummaryComponent.ticketData);
  });
});
