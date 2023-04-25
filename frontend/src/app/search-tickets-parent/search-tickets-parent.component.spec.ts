import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchTicketsParentComponent} from './search-tickets-parent.component';
import {TicketType} from "./search-tickets/search-tickets.component";
import {Component, Input} from "@angular/core";
import {TicketData} from "./data/ticket-data";

@Component ({
  selector: 'app-search-connection',
  template: '<p id="welcome">{{helloMessage}}</p>'
})
class MockSearchTickets{
  helloMessage = "Search connection works";
}

@Component ({
  selector: 'app-ticket-summary',
  template: '<p id="welcome">{{helloMessage}}</p>'
})
class MockTicketSummary{
  helloMessage = "Ticket summary works";
  @Input() ticketData!: TicketData;
}

describe('SearchTicketsParentComponent', () => {
  let parentComponent: SearchTicketsParentComponent;
  let parentFixture: ComponentFixture<SearchTicketsParentComponent>;
  let searchTicketsMock: MockSearchTickets;
  let ticketSummaryMock: MockTicketSummary;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchTicketsParentComponent, MockSearchTickets, MockTicketSummary ],
    })
    .compileComponents();

    searchTicketsMock= TestBed.createComponent(MockSearchTickets).componentInstance;
    ticketSummaryMock= TestBed.createComponent(MockTicketSummary).componentInstance;
    parentFixture = TestBed.createComponent(SearchTicketsParentComponent);
    parentComponent = parentFixture.componentInstance;
    parentFixture.detectChanges();
  });

  it('should get ticket data on submit', () => {
    let ticketData = {
      fromStation: "from",
      toStation: "to",
      connectionId: 1,
      travelDate: "12-12-2003",
      ticketType: TicketType.FULL_FARE
    }
    parentComponent.onSubmit(ticketData);
    expect(parentComponent.ticketData).toBe(ticketData);
    expect(parentComponent.isSubmitted).toBeTruthy();
  });
  it('should show search tickets parentComponent on first load', () => {
    let welcomeText = parentFixture.nativeElement.querySelector("#welcome");
    expect(welcomeText.textContent).toBe(searchTicketsMock.helloMessage);
  });
  it('should show summary page when submitted data', () => {
    parentComponent.isSubmitted = true;
    parentFixture.detectChanges();
    let welcomeText = parentFixture.nativeElement.querySelector("#welcome");
    expect(welcomeText.textContent).toBe(ticketSummaryMock.helloMessage);
  });
});
