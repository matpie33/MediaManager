import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConnectionData} from "../components/search-tickets-parent/data/connection-data";
import {Observable} from "rxjs";
import {TicketData} from "../components/search-tickets-parent/data/ticket-data";
import {TicketPdfData, UserTicket} from "../components/tickets-management/data/ticket-of-user";
import {LoginData, LoginResponse, PersonalData, RegisterData} from "../components/login/data/login-data";
import {REST_API_URL} from "../constants/environment/environment";
import {TrainData} from "../components/add-connection/data/train-data";
import {Roles, UserRoles} from "../components/manage-users/data/user-roles";

@Injectable({
  providedIn: 'root'
})
export class RestClientService {

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
    return this.httpClient.post(`${this.restBaseAddress}/addUser`, credentials );
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

  getTrains (){
    return this.httpClient.get<Set<TrainData>>(`${this.restBaseAddress}/trains`);

  }

  addConnection (from: string, to: string, time:string, trainId: number){
    return this.httpClient.post(`${this.restBaseAddress}/connection/from/${from}/to/${to}/atTime/${time}/trainId/${trainId}`, "");
  }

  getTicketAsPdf (ticketData: TicketPdfData, options: any){
    return this.httpClient.post(`${this.restBaseAddress}/ticket/pdf`, ticketData, options);
  }

  getUsersRoles (){
    return this.httpClient.get<Array<UserRoles>>(`${this.restBaseAddress}/users/roles`);
  }

  editUserRoles(username: string, roles: Roles){
    return this.httpClient.post(`${this.restBaseAddress}/user/${username}/roles`, roles);
  }

}
