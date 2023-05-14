import { Component } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";

@Component({
  selector: 'app-qr-code-scanner',
  templateUrl: './qr-code-scanner.component.html',
  styleUrls: ['./qr-code-scanner.component.css']
})
export class QrCodeScannerComponent {

  scanFinished = false;
  scannedValue="";

  constructor(private restClientService: RestClientService) {
  }


  scanSuccesss(scannedValue: string) {
    this.scanFinished = true;
    this.restClientService.decodeQrCode(scannedValue)
      .subscribe(returnValue=>this.scannedValue=returnValue.content);
  }

  reset() {
    this.scanFinished=false;
  }
}
