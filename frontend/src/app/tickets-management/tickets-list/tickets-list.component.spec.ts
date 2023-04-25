import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsListComponent } from './tickets-list.component';
import {of} from "rxjs";
import {TicketType} from "../../search-tickets-parent/search-tickets/search-tickets.component";
import {RestHandlerService} from "../../rest-handler.service";

describe('TicketsListComponent', () => {
  let component: TicketsListComponent;
  let fixture: ComponentFixture<TicketsListComponent>;


  beforeEach(async () => {
    const restHandler = jasmine.createSpyObj("RestHandlerService",
      ["getTicketsOfUser"]);
    await TestBed.configureTestingModule({
      declarations: [ TicketsListComponent ],
      providers: [{provide: RestHandlerService, useValue: restHandler}]
    })
    .compileComponents();
    restHandler.getTicketsOfUser.and.returnValue(of([{
      travelDate: "12-12-2002",
      ticketType: TicketType.REDUCED,
      connection : {
        fromStation: "Wroclaw",
        toStation: "Przemysl",
        time: "12:22"
      }
    }]));

    fixture = TestBed.createComponent(TicketsListComponent);
    component = fixture.componentInstance;

  });

  it('should show ticket', () => {
    let h1 = fixture.nativeElement.querySelector("h1");
    expect(h1).toBeNull();
    fixture.detectChanges();
    expect(component.tickets).toHaveSize(1);
    let pElements = fixture.nativeElement.querySelectorAll(".bordered p");
    expect(pElements).toHaveSize(4);
    expect(pElements[0].textContent).toContain("Wroclaw");
    expect(pElements[1].textContent).toContain("Przemysl");
    expect(pElements[2].textContent).toContain("12.12.2002 12:22");
    expect(pElements[3].textContent).toContain("reduced");

    h1 = fixture.nativeElement.querySelector("h1");
    expect(h1.textContent).toBe("Tickets history:");
  });
});
