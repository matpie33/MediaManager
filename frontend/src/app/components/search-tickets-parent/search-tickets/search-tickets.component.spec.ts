import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchTicketsComponent} from './search-tickets.component';
import {of} from "rxjs";
import {RestClientService} from "../../../services/rest-client.service";
import {CommonModule} from "@angular/common";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule} from "@angular/forms";
import {TicketType} from "../data/ticket-data";

describe('SearchConnectionComponent', () => {
  let restHandlerService: any;
  let searchTickets: SearchTicketsComponent;
  let fixture: ComponentFixture<SearchTicketsComponent>;
  beforeEach(async () => {
    restHandlerService = jasmine.createSpyObj(RestClientService.name, [RestClientService.prototype.getConnections.name]);
    TestBed.configureTestingModule({
      providers: [
        {provide: RestClientService, useValue: restHandlerService}
      ],
      imports: [
        BrowserModule, CommonModule, ReactiveFormsModule
      ],
      declarations: [
        SearchTicketsComponent
      ]
    });
    fixture = TestBed.createComponent(SearchTicketsComponent);
    searchTickets = fixture.componentInstance;

  });

  it('should load tickets and show on UI', () => {
    searchTickets.searchForm.controls["fromStation"].setValue("ab");
    searchTickets.searchForm.controls["toStation"].setValue("ab");
    searchTickets.searchForm.controls["date"].setValue("12/12/1993");
    searchTickets.searchForm.controls["time"].setValue("12:20");
    let connection1 = {
      id: 1,
      freeSeats: 1,
      fromStation: "ab",
      toStation: "bc",
      time: "12:20"
    };
    let connection2 = {
      id: 2,
      freeSeats: 4,
      fromStation: "de",
      toStation: "fe",
      time: "15:20"
    };
    restHandlerService.getConnections.and.returnValue(of([connection1, connection2]));
    searchTickets.onSubmitSearch();
    expect(searchTickets.availableTickets).toHaveSize(2);
    expect(searchTickets.availableTickets.values()).toContain(connection2);
    expect(searchTickets.availableTickets.values()).toContain(connection1);
    fixture.detectChanges();
    let divNumber = 1;
    for(let div of fixture.nativeElement.querySelectorAll(".connections-parent div")){
      expect(div.children[0].textContent).toBe("Time");
      expect(div.children[1].textContent).toBe(divNumber == 1? "12:20": "15:20");
      expect(div.children[2].textContent).toBe("Free seats:");
      expect(div.children[3].textContent).toBe(divNumber == 1? "1": "4");
      divNumber++;
    }
  });

  it('should emit data on submit', () => {
    searchTickets.searchForm.controls["date"].setValue("12/12/1993");
    let connection1 = {
      id: 1,
      freeSeats: 1,
      fromStation: "ab",
      toStation: "bc",
      time: "12:20"
    };
    let connection2 = {
      id: 2,
      freeSeats: 4,
      fromStation: "de",
      toStation: "fe",
      time: "15:20"
    };
    spyOn(searchTickets.ticketData, "emit")
    searchTickets.availableTickets.set(1, connection1);
    searchTickets.availableTickets.set(2, connection2);
    searchTickets.ticketsForm.controls["ticket"].setValue(1);
    searchTickets.ticketsForm.controls["ticketType"].setValue("reduced");
    searchTickets.onSubmitPersonalData();
    expect(searchTickets.ticketData.emit).toHaveBeenCalledWith({
      fromStation: "ab",
      toStation: "bc",
      connectionId: 1,
      travelDate: "12.12.1993 12:20",
      ticketType: TicketType.REDUCED
    });
  });

});
