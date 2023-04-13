import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TicketData} from "../data/ticket-data";
import { ConnectionData } from '../data/connection-data';
import {RestHandlerService} from "../../rest-handler.service";

@Component({
  selector: 'app-search-connection',
  templateUrl: './search-tickets.component.html',
  styleUrls: ['./search-tickets.component.css']
})
export class SearchTickets {

  public ticketTypeEnum = TicketType;
  searchForm: FormGroup  = new FormGroup<any>({
    fromStation:  new FormControl("", Validators.required),
    toStation: new FormControl("", Validators.required),
    date: new FormControl(""),
    time: new FormControl("00:00:00")
  });
  ticketsForm: FormGroup = new FormGroup({
    ticket: new FormControl("", Validators.required),
    ticketType: new FormControl(TicketType.FULL_FARE.valueOf())
  });

  searchPerformed: boolean = false;
  searchFormValid :boolean = false;
  personalDataFormValid = true;

  @Output() ticketData: EventEmitter<TicketData> = new EventEmitter<TicketData>();

  availableTickets: Map<number,ConnectionData> = new Map();

  constructor(private restHandler: RestHandlerService) {
  }

  onSubmitSearch() {
    this.availableTickets.clear();
    if (this.searchForm.valid){
      this.searchPerformed = true;
      this.searchFormValid = false;
      this.restHandler.getConnections(this.searchForm.controls["fromStation"].value,
        this.searchForm.controls["toStation"].value, this.searchForm.controls["time"].value).subscribe(connections=>{
          connections.forEach(connection =>{
            this.availableTickets.set(connection.id, connection);
          });
        console.log(this.availableTickets);
      });
    }
    else{
      this.searchFormValid = true;
      this.searchPerformed = false;
    }
  }


  onSubmitPersonalData (){
    if (this.ticketsForm.valid){
      this.personalDataFormValid = true;
      let connectionData: ConnectionData = this.availableTickets.get( this.ticketsForm.controls["ticket"].value)!;
      let ticketData: TicketData = {
        fromStation: connectionData.fromStation,
        toStation: connectionData.toStation,
        time: connectionData.time,
        connectionId: this.ticketsForm.controls["ticket"].value,
        date: this.searchForm.controls["date"].value,
        ticketType: this.ticketsForm.controls["ticketType"].value
      };
      this.ticketData.emit(ticketData);
    }
    else{
      this.personalDataFormValid = false;
    }
  }

}

export enum TicketType {
  REDUCED = "reduced", FULL_FARE="full-fare"
}
