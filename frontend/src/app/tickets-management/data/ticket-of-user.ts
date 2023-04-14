import {TicketType} from "../../search-tickets-parent/search-tickets/search-tickets.component";

export interface UserTicket {
  travelDate: Date;
  ticketType: TicketType;
  connection : {
    fromStation: string,
    toStation: string,
    time: string

  }
}

