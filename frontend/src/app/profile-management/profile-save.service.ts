import { Injectable } from '@angular/core';
import {delay, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProfileSaveService {

  saveUser(profileData: { firstName: any; lastName: any; email: any }): Observable<boolean>{
    return of(true).pipe(delay(1000));
  }
}
