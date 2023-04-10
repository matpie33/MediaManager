import {Time} from "@angular/common";
import {TicketType} from "../../search-tickets-parent/search-tickets/search-tickets.component";

export interface BoughtTicketData {
  fromStation: string;
  toStation: string;
  date: Date;
  time: Time;
  ticketType: TicketType;
}

export var BOUGHT_TICKETS_MOCK: BoughtTicketData[] = [
  {
    fromStation: "Wro",
    toStation: "przemysl",
    date: new Date(),
    time: {
      hours: 10,
      minutes: 5
    },
    ticketType: TicketType.REDUCED,
  },
  {
    fromStation: "Krakow",
    toStation: "jaroslaw",
    date: new Date(),
    time: {
      hours: 5,
      minutes: 55
    },
    ticketType: TicketType.FULL_FARE,
  },{
    fromStation: "Wro",
    toStation: "Dębica",
    date: new Date(),
    time: {
      hours: 15,
      minutes: 5
    },
    ticketType: TicketType.REDUCED,
  },
]
