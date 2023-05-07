import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {UserRoles} from "./data/user-roles";
import {Role} from "../../constants/role";

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit{

  userRoles: Map<string, Array<string>> = new Map<string, Array<string>>();
  currentUserRoles = new Set<string>();
  status = "";
  roleTypes = Role;


  constructor(private restClient: RestClientService) {

  }

  ngOnInit() {
    this.restClient.getUsersRoles().subscribe(users=>{
      for (let user of users){
        this.userRoles.set(user.userName, user.roles);
      }
    })
  }


  inputChanged(input: any) {
    let roles = this.userRoles.get(input.value);
    if (roles){
      if (roles.length >0){
        roles.forEach(role=>this.currentUserRoles.add(role));
      }
      else{
        this.status = "No roles";
        this.currentUserRoles.add( "none");
      }
    }
    else{
      this.status = "User does not exist";
      this.currentUserRoles.clear();

    }

  }

  userNotPossessedRoles (){
    let notPossessed = new Set<string>();
    for (let role of Object.keys(this.roleTypes)){
      if (!this.currentUserRoles.has(role)){
        notPossessed.add(role);
      }
    }
    return notPossessed;
  }

  saveChanges() {

  }

  clear(input: HTMLInputElement) {
    input.value = "";
  }

  addRole(selectedRole: HTMLSelectElement) {
    if (this.currentUserRoles.has("none")){
      this.currentUserRoles.clear();
    }
    this.currentUserRoles.add(selectedRole.value);
  }
}
