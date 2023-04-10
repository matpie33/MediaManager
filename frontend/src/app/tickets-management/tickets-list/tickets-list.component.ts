import { Component } from '@angular/core';
import {BOUGHT_TICKETS_MOCK} from "../data/bought-ticket-data";

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.css']
})
export class TicketsListComponent {
 tickets = BOUGHT_TICKETS_MOCK;
}
