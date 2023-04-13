import {TicketType} from "../search-tickets/search-tickets.component";

export interface TicketData {
  fromStation: string,
  toStation: string,
  time: string,
  connectionId: number;
  date: Date;
  ticketType: TicketType;

}

