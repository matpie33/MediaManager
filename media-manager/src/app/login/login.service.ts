import { Injectable } from '@angular/core';
import {delay, Observable, of} from "rxjs";
import {LOGIN_DATA_MOCK, LoginData, RegisterData} from "./login-data";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isLoggedIn = false;
  loginData: Map<string, string> = new Map<string, string>();

  constructor() {
    for (let login of LOGIN_DATA_MOCK){
      this.loginData.set(login.username, login.password);
    }
  }

  login (credentials: LoginData): Observable<boolean>{
    let result:boolean = false;
    if (this.loginData.get(credentials.username) === credentials.password){
      result = true;
      this.isLoggedIn = true;
    }
    return of(result).pipe(delay(1000));
  }

  register (credentials: RegisterData): Observable<boolean>{
    let result:boolean = false;
    if (!this.loginData.has(credentials.username)){
      result = true;
    }
    return of(result).pipe(delay(1000));
  }

}
