import {delay, Observable, of} from "rxjs";
import {TicketData} from "./data/ticket-data";

export class BuyTicketService {

  constructor() { }

  buyTicket(ticketData: TicketData) :Observable<boolean>{
    return of(true).pipe(delay(3000));
  }

}
