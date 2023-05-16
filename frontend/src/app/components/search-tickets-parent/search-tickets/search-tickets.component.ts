import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TicketData, TicketType} from "../data/ticket-data";
import { ConnectionData } from '../data/connection-data';
import {RestClientService} from "../../../services/rest-client.service";
import {DatePipe, KeyValue} from "@angular/common";
import {DATE_FORMAT, HTML_DATE_INPUT_FORMAT} from "../../../constants/date-formats";
import {ViewWithStatus} from "../../common/view-with-status";

@Component({
  selector: 'app-search-connection',
  templateUrl: './search-tickets.component.html',
  styleUrls: ['./search-tickets.component.css']
})
export class SearchTicketsComponent extends ViewWithStatus{

  todayDate: string = new DatePipe("en").transform(new Date(), HTML_DATE_INPUT_FORMAT)!;

  ticketTypeEnum = TicketType;
  searchForm: FormGroup  = new FormGroup<any>({
    fromStation:  new FormControl("", Validators.required),
    toStation: new FormControl("", Validators.required),
    date: new FormControl(this.todayDate, Validators.required),
    time: new FormControl("00:00")
  });
  ticketsForm: FormGroup = new FormGroup({
    ticket: new FormControl("", Validators.required),
    ticketType: new FormControl(TicketType.FULL_FARE.valueOf())
  });

  searchPerformed: boolean = false;
  searchFormValid :boolean = false;
  loadingData = false;

  @Output() ticketData: EventEmitter<TicketData> = new EventEmitter<TicketData>();

  availableTickets: Map<number,ConnectionData> = new Map();

  connectionByTimeComparator = (a: KeyValue<number,ConnectionData>, b: KeyValue<number,ConnectionData>): number => {
    return a.value.time.localeCompare(b.value.time);
  }


  constructor(private restHandler: RestClientService) {
    super();
  }

  onSubmitSearch() {
    this.availableTickets.clear();
    this.loadingData = true;
    if (this.searchForm.valid){
      this.searchPerformed = true;
      this.searchFormValid = false;
      let dateAndTime = new DatePipe("en").transform(this.searchForm.controls["date"].value, "dd.MM.y") + " "+this.searchForm.controls["time"].value;
      this.restHandler.getConnections(this.searchForm.controls["fromStation"].value,
        this.searchForm.controls["toStation"].value, dateAndTime).subscribe(connections=>{
          connections.forEach(connection =>{
            this.availableTickets.set(connection.id, connection);
          });
          this.loadingData = false;
      });
    }
    else{
      this.searchFormValid = true;
      this.searchPerformed = false;
    }
  }


  onSubmitPersonalData (){
    if (this.ticketsForm.valid){
      let connectionData: ConnectionData = this.availableTickets.get( this.ticketsForm.controls["ticket"].value)!;
      let date = this.searchForm.controls["date"].value;
      let convertedDate = new DatePipe("en").transform(date, DATE_FORMAT);
      let ticketData: TicketData = {
        fromStation: connectionData.fromStation,
        toStation: connectionData.toStation,
        connectionId: this.ticketsForm.controls["ticket"].value,
        travelDate: convertedDate + " " + connectionData.time,
        ticketType: this.ticketsForm.controls["ticketType"].value
      };
      this.ticketData.emit(ticketData);
    }
    else{

        this.showErrorMessage("Please select connection");
    }
  }

}


