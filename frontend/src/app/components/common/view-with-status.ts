import {StatusCssClass} from "../../constants/status-css-class";

export class ViewWithStatus {

  statusClass = "";
  statusMessage = "";
  showStatus = false;
  defaultDelay = 3;

  showInfoMessage (message: string){
    this.statusMessage = message;
    this.statusClass = StatusCssClass.INFORMATION;
    this.showStatus = true;
  }

  showSuccessMessage (message: string){
    this.statusMessage = message;
    this.statusClass = StatusCssClass.SUCCESS;
    this.showStatus = true;
  }

  showErrorMessage (message: string){
    this.statusMessage = message;
    this.statusClass = StatusCssClass.ERROR;
    this.showStatus = true;
  }

  hideStatusAfterDelay(callback?: any, delay?: number): number{
    return setTimeout(()=> {
      this.showStatus = false;
      if (callback){
        callback();
      }
    },  (delay? delay: this.defaultDelay) * 1000);
  }


}
