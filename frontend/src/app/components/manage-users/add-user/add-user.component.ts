import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Role} from "../../../constants/role";
import {ViewWithStatus} from "../../common/view-with-status";
import {StatusCssClass} from "../../../constants/status-css-class";
import {RestClientService} from "../../../services/rest-client.service";
import * as crypto from "crypto-js";
import {UserAuthenticationService} from "../../../services/user-authentication.service";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent extends ViewWithStatus{
  roleTypes = Role;
  currentUserRoles = new Set<string>();
  addUserForm = this.passwordService.registerForm;

  constructor(private passwordService: UserAuthenticationService) {
    super();
    this.currentUserRoles.add("none");
  }

  onSubmit() {
    if (this.passwordService.passwordsMatch()){
      this.showErrorMessage("Passwords do not match");
    }
    else{
      let roles = Array.from(this.currentUserRoles);
      this.passwordService.register(roles).subscribe({
        complete: ()=>{
            this.showSuccessMessage("Successfully saved data");
        },
        error: () => {
          this.showErrorMessage("Error registering user");
        }
      });
    }
  }

  remove(currentRole: any, roleCheckbox: HTMLInputElement) {
    if (roleCheckbox.checked){
      this.currentUserRoles.add(currentRole);
    }
    else{
      this.currentUserRoles.delete(currentRole);
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

  addRole(selectedRole: HTMLSelectElement) {
    if (this.currentUserRoles.has("none")){
      this.currentUserRoles.clear();
    }
    this.currentUserRoles.add(selectedRole.value);
  }
}
