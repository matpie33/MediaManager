import { Component } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {QrCodeData} from "./data/qr-code-data";

@Component({
  selector: 'app-qr-code-scanner',
  templateUrl: './qr-code-scanner.component.html',
  styleUrls: ['./qr-code-scanner.component.css']
})
export class QrCodeScannerComponent {

  ticketData: QrCodeData | undefined;
  waitingForQrCodeResponse = false;
  scanFinished = false;

  constructor(private restClientService: RestClientService) {
  }


  scanSuccess(scannedValue: string) {
    this.waitingForQrCodeResponse = true;
    this.scanFinished = true;
    this.restClientService.decodeQrCode(scannedValue)
      .subscribe(returnValue=>{this.ticketData=returnValue; this.waitingForQrCodeResponse = false});
  }

  reset() {
    this.scanFinished = false;
    this.ticketData = undefined;
  }
}
