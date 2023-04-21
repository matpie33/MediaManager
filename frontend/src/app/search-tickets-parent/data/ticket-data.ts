import {TicketType} from "../search-tickets/search-tickets.component";

export interface TicketData {
  fromStation: string,
  toStation: string,
  connectionId: number;
  travelDate: string;
  ticketType: TicketType;

}

