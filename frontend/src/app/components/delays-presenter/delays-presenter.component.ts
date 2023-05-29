import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {LoginConstants} from "../login/data/login-enums";
import {TicketWithDelay} from "./data/TicketWithDelay";

@Component({
  selector: 'app-delays-presenter',
  templateUrl: './delays-presenter.component.html',
  styleUrls: ['./delays-presenter.component.css']
})
export class DelaysPresenterComponent implements OnInit{
  relations: Map<number, TicketWithDelay>  = new Map<number, TicketWithDelay>();
  currentRelation: TicketWithDelay | undefined;
  loadingData = true;

  constructor(private restClient: RestClientService) {
  }

  ngOnInit(): void {
    this.restClient.getTrainsWithDelaysNow(sessionStorage.getItem(LoginConstants.USER_ID)!)
      .subscribe(relations=> {
        let i=0;
        for (let relation of relations){
          this.relations.set(i, relation);
          i++;
        }
        this.currentRelation = this.relations.get(0);
        this.loadingData = false;
      });
  }



  onRelationSelect($event: Event) {
    let relationId = (<HTMLSelectElement>($event.target!)).value;
    this.currentRelation = this.relations.get(Number.parseInt(relationId));
  }
}
