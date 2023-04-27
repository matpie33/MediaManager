import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsManagementComponent } from './tickets-management.component';
import {Component} from "@angular/core";

@Component ({
  template: "<h1>{{message}}</h1>",
  selector: "app-tickets-list"
})
class TicketsListMock {
  message = "Tickets list";
}

describe('TicketsManagementComponent', () => {
  let component: TicketsManagementComponent;
  let fixture: ComponentFixture<TicketsManagementComponent>;
  let ticketsListMock: TicketsListMock;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TicketsManagementComponent, TicketsListMock ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketsManagementComponent);
    ticketsListMock = TestBed.createComponent(TicketsListMock).componentInstance;
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create view', () => {
    expect(component).toBeTruthy();
    expect(fixture.nativeElement.querySelector("h1").textContent).toBe(ticketsListMock.message);
  });
});
