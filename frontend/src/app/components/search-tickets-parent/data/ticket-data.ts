
export interface TicketData {
  fromStation: string,
  toStation: string,
  connectionId: number;
  travelDate: string;
  ticketType: TicketType;
}

export enum TicketType {
  REDUCED = "reduced", FULL_FARE="full-fare"
}

