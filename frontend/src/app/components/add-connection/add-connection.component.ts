import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RestHandlerService} from "../../services/rest-handler.service";
import {StatusCssClass} from "../../constants/status-css-class";

@Component({
  selector: 'app-add-connection',
  templateUrl: './add-connection.component.html',
  styleUrls: ['./add-connection.component.css']
})
export class AddConnectionComponent {
  form: FormGroup = this.formBuilder.group({
    fromStation: "",
    toStation: "",
    time: "11:11",
    train: ""
  });

  trains: Map<number, String> = new Map<number, String>();
  showStatus = false;
  statusClass = "";
  statusMessage = "";
  timeoutId = 0;
  SUCCESS_MESSAGE = "Successfully added travel connection.";
  ERROR_MESSAGE = "Error while adding travel connection.";

  constructor(private formBuilder: FormBuilder, private restHandlerService: RestHandlerService) {

  }

  ngOnInit (){
    this.restHandlerService.getTrains().subscribe(trains=>{
      for (let train of trains){
        this.trains.set(train.id, train.name);
      }
      this.form.controls["train"].setValue(1);
    });

  }

  onSubmit (){
    this.restHandlerService.addConnection(this.form.controls["fromStation"].value,
      this.form.controls["toStation"].value, this.form.controls["time"].value, this.form.controls["train"].value ).subscribe({
        next: this.handleSuccess.bind(this),
        error: this.handleError.bind(this)
    });
  }



  handleSuccess(){
    this.statusClass = StatusCssClass.SUCCESS;
    this.statusMessage = this.SUCCESS_MESSAGE;
    this.showStatus = true;
    this.timeoutId = setTimeout(()=>{
      this.showStatus = false;
    }, 4000);
  }

  handleError (){
    this.showStatus = true;
    this.statusClass = StatusCssClass.ERROR;
    this.statusMessage = this.ERROR_MESSAGE;
    clearTimeout(this.timeoutId);
  }

}
