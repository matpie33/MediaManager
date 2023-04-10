import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SearchData} from "../data/search-data";
import {Time} from "@angular/common";
import {TicketData} from "../data/ticket-data";
import {MOCKED_DATA, ConnectionData} from "../data/connection-data";
import {tick} from "@angular/core/testing";

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
    time: new FormControl("00:00")
  });
  ticketsForm: FormGroup = new FormGroup({
    ticket: new FormControl("", Validators.required),
    ticketType: new FormControl(TicketType.FULL_FARE.valueOf())
  });

  searchPerformed: boolean = false;
  searchFormValid :boolean = false;
  personalDataFormValid = true;

  @Input() searchData!: SearchData;
  @Output() ticketData: EventEmitter<TicketData> = new EventEmitter<TicketData>();

  availableTickets: Map<number,ConnectionData> = new Map();

  onSubmitSearch() {
   let  hours: String = this.searchForm.controls["time"].value;
   let time: Time = {
     hours: Number.parseInt(hours.substring(0,2)),
     minutes : Number.parseInt(hours.substring(3,5))
   }
    this.searchData = {
      fromStation: this.searchForm.controls["fromStation"].value,
      toStation: this.searchForm.controls["toStation"].value,
      date: this.searchForm.controls["date"].value,
      time: time
    }
    if (this.searchForm.valid){
      this.searchPerformed = true;
      this.searchFormValid = false;
      this.searchTickets();
    }
    else{
      this.searchFormValid = true;
      this.searchPerformed = false;
    }
  }

  searchTickets(){
    this.availableTickets.clear();
    MOCKED_DATA.forEach(ticket => {
      if (ticket.time.hours > this.searchData.time.hours){
        this.availableTickets.set(ticket.id, ticket);
      }
    });
  }

  onSubmitPersonalData (){
    if (this.ticketsForm.valid){
      this.personalDataFormValid = true;
      let ticketData: TicketData = {
        fromStation: this.searchData.fromStation,
        toStation: this.searchData.toStation,
        date: this.searchData.date,
        time: this.availableTickets.get( this.ticketsForm.controls["ticket"].value)!.time,
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
