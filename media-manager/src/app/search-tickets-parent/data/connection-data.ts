import {Time} from "@angular/common";

export interface ConnectionData {
  id: number;
  time: Time;
  freeSeats: number;
}

export var MOCKED_DATA: ConnectionData[] = [
    {
      id: 1,
    time: {hours: 10, minutes: 30},
    freeSeats: 5
  },
    {
      id: 2,
      time: {hours: 15, minutes: 30},
      freeSeats: 2
    },{
      id: 3,
      time: {hours: 18, minutes: 45},
      freeSeats: 2
    },
  ]

