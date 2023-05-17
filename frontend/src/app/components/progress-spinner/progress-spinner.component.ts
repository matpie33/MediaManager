import {Component, Input} from '@angular/core';
import {ProgressSpinnerMode} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-progress-spinner',
  templateUrl: './progress-spinner.component.html',
  styleUrls: ['./progress-spinner.component.css']
})
export class ProgressSpinnerComponent {

  mode: ProgressSpinnerMode = 'indeterminate';
  @Input() shouldDisplay = false;
  @Input() diameter = 80;

}
