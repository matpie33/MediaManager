import {AfterContentChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {RestClientService} from "../../../services/rest-client.service";
import {ConnectionData} from "../../search-tickets-parent/data/connection-data";
import {DatePipe} from "@angular/common";
import {DATE_FORMAT, HTML_DATE_INPUT_FORMAT} from "../../../constants/date-formats";
import {ViewWithStatus} from "../../common/view-with-status";

@Component({
  selector: 'app-add-delay',
  templateUrl: './add-delay.component.html',
  styleUrls: ['./add-delay.component.css']
})
export class AddDelayComponent extends ViewWithStatus implements OnInit{

  delayForm = this.formBuilder.group({
    delayValue: "",
    date: "",
    connectionId: ""
  })

  connections: Map<number, ConnectionData> = new Map<number, ConnectionData>();

  constructor(private formBuilder: FormBuilder, private httpService: RestClientService, private changeDetector: ChangeDetectorRef) {
    super();
  }

  getConnectionTime(selectedConnectionId: string){
    return this.connections.get( Number.parseInt(selectedConnectionId))?.time;
  }

  onSubmit() {
    let date = new DatePipe("en").transform(this.delayForm.controls["date"].value!, DATE_FORMAT)!;
    let connectionDelay = {
      connectionId: Number.parseInt(this.delayForm.controls["connectionId"].value!),
      delay: Number.parseInt(this.delayForm.controls["delayValue"].value!),
      url: "www.google.pl/tobedone"
    }
    this.httpService.sendDelayNotification(connectionDelay).subscribe(()=>console.log("sent notification"));
    this.showInfoMessage("Adding delay...");
    this.httpService.addDelay(this.delayForm.controls["connectionId"].value!, date,
      this.delayForm.controls["delayValue"].value!).subscribe(()=> {
      this.showSuccessMessage("Successfully added delay");
      this.hideStatusAfterDelay();
    });

  }


  connectionToString (connection: ConnectionData){
    return connection.fromStation + " - " + connection.toStation;
  }

  ngOnInit(): void {
    this.httpService.getAllConnections().subscribe(value=>{
      for (let connection of value){
        this.connections.set(connection.id, connection);
      }
      let [firstConnection] = this.connections.values();
      this.delayForm.controls["connectionId"].setValue("" + firstConnection.id);
      this.changeDetector.detectChanges();
    });
  }

}
