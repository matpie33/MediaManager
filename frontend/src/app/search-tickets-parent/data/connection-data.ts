import {Time} from "@angular/common";

export interface ConnectionData {
  id: number;
  time: string;
  freeSeats: number;
  fromStation: string;
  toStation: string;
  date: Date;
}


