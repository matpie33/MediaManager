import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConnectionData} from "./search-tickets-parent/data/connection-data";
import {Observable} from "rxjs";
import {TicketData} from "./search-tickets-parent/data/ticket-data";
import {UserTicket} from "./tickets-management/data/ticket-of-user";
import {LoginData, LoginResponse, PersonalData, RegisterData} from "./login/login-data";
import {REST_API_URL} from "./environment/environment";

@Injectable({
  providedIn: 'root'
})
export class RestHandlerService {

  constructor(private httpClient: HttpClient) {
  }

  restBaseAddress = REST_API_URL;

  getConnections (fromStation: string, toStation: string, time: string) : Observable<Array<ConnectionData>>{
    return this.httpClient.get<Array<ConnectionData>>(`${this.restBaseAddress}/connection/${fromStation}/to/${toStation}/sinceHour/${time}`);

  }

  assignTicketToUser(userId: number, ticketData: TicketData) {
    return this.httpClient.get<boolean>(`${this.restBaseAddress}/assignTicket/${ticketData.connectionId}/user/${userId}/ticket_type/${ticketData.ticketType}/travelDate/${ticketData.travelDate}`);

  }

  getTicketsOfUser(userId: number) {
    return this.httpClient.get<Array<UserTicket>>(`${this.restBaseAddress}/tickets/${userId}`);
  }
  loginUser(credentials: LoginData) {
    return this.httpClient.post<LoginResponse>(`${this.restBaseAddress}/login`, credentials );
  }

  registerUser(credentials: RegisterData) {
    return this.httpClient.post<boolean>(`${this.restBaseAddress}/addUser`, credentials );
  }

  editUser(personalData: PersonalData, userId: number) {
    return this.httpClient.post<boolean>(`${this.restBaseAddress}/editUser/${userId}`, personalData );
  }

  getUser (userId: number){
    return this.httpClient.get<PersonalData>(`${this.restBaseAddress}/getUser/${userId}`);

  }

  getUserPermissions (userId: number){
    return this.httpClient.get<LoginResponse>(`${this.restBaseAddress}/permissions/${userId}`);

  }


}
