import {TicketType} from "../../search-tickets-parent/data/ticket-data";

export interface UserTicket {
  travelDate: Date;
  ticketType: TicketType;
  connection : {
    fromStation: string,
    toStation: string,
    time: string

  }
}

