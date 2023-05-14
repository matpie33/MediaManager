import { Component } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {QrCodeData} from "./data/qr-code-data";

@Component({
  selector: 'app-qr-code-scanner',
  templateUrl: './qr-code-scanner.component.html',
  styleUrls: ['./qr-code-scanner.component.css']
})
export class QrCodeScannerComponent {

  scanFinished = false;
  ticketData!: QrCodeData;

  constructor(private restClientService: RestClientService) {
  }


  scanSuccesss(scannedValue: string) {
    this.scanFinished = true;
    this.restClientService.decodeQrCode(scannedValue)
      .subscribe(returnValue=>this.ticketData=returnValue);
  }

  reset() {
    this.scanFinished=false;
  }
}
