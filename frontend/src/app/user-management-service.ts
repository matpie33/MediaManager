import {Injectable} from "@angular/core";
import {Observable, of, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {
  username: Subject<string> = new Subject<string>();

  userLogin (username: string){
    this.username.next(username);
  }


}
