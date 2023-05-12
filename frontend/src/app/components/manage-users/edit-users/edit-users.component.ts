import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../../services/rest-client.service";
import {Roles, UserRoles} from "../data/user-roles";
import {Role} from "../../../constants/role";
import {ViewWithStatus} from "../../common/view-with-status";
import {StatusCssClass} from "../../../constants/status-css-class";
import {RoleService} from "../../../services/role.service";

@Component({
  selector: 'app-edit-users',
  templateUrl: './edit-users.component.html',
  styleUrls: ['./edit-users.component.css'],
  providers: [RoleService]
})
export class EditUsersComponent extends ViewWithStatus implements OnInit{

  userRoles: Map<string, Array<string>> = new Map<string, Array<string>>();
  currentUserRoles = this.roleService.currentUserRoles;
  status = "";
  roleTypes = Role;
  userName = "";


  constructor(private restClient: RestClientService, private roleService: RoleService) {
    super();
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
    this.userName = input.value;
    this.currentUserRoles.clear();
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
    return this.roleService.userNotPossessedRoles();
  }

  saveChanges(buttonSave: HTMLButtonElement) {
    let roles: Roles = {
      roles: Array.from(this.currentUserRoles)
    }
    this.restClient.editUserRoles(this.userName, roles).subscribe({
      complete: () => {
        this.statusMessage = "Successfully edited roles";
        this.statusClass = StatusCssClass.SUCCESS;
        this.showStatus = true;
        this.userRoles.set(this.userName, Array.from(this.currentUserRoles));
        buttonSave.disabled = true;
        this.hideStatusAfterDelay()
      },
      error: () => {
        this.statusMessage = "Failed to edit roles";
        this.statusClass = StatusCssClass.ERROR},
    });

  }

  clear(input: HTMLInputElement) {
    input.value = "";
  }

  addRole(selectedRole: HTMLSelectElement, buttonSave: HTMLButtonElement) {
    this.roleService.addRole(selectedRole);
    buttonSave.disabled = false;
  }

  remove(currentRole: string, buttonSave: HTMLButtonElement) {
    this.roleService.remove(currentRole);
    buttonSave.disabled = false;
  }
}
