import {TicketType} from "../../search-tickets-parent/data/ticket-data";

export interface UserTicket {
  travelDate: Date;
  ticketType: TicketType;
  trainName: string;
  connection: {
    fromStation: string,
    toStation: string,
    time: string

  }
  id: number;
  isLoading: boolean;
}

export interface TicketPdfData {
  firstName: string;
  lastName: string;
  email: string;
  travelDate: string;
  fromStation: string;
  toStation: string;
  ticketType: string;
  trainName: string;
  trainTime: string;

}

