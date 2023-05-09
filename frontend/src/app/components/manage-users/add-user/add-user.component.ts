import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Role} from "../../../constants/role";
import {ViewWithStatus} from "../../common/view-with-status";
import {StatusCssClass} from "../../../constants/status-css-class";
import {RestClientService} from "../../../services/rest-client.service";
import * as crypto from "crypto-js";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent extends ViewWithStatus{
  roleTypes = Role;
  currentUserRoles = new Set<string>();
  addUserForm: FormGroup = this.formBuilder.group({
    username: "",
    password: "",
    passwordConfirm: ""
  })
  encryptionKey = "R0U8T7MFBAXfJe6DpHeM";

  constructor(private formBuilder: FormBuilder, private restClientService: RestClientService) {
    super();
    this.currentUserRoles.add("none");
  }

  onSubmit() {
    if (this.addUserForm.value["password"] !== this.addUserForm.value["passwordConfirm"]){

      this.statusMessage = "Passwords do not match";
      this.statusClass = StatusCssClass.ERROR;
    }
    else{
      let registerData = {
        roles: Array.from(this.currentUserRoles),
        personalData: {
          firstName: "",
          lastName: "",
          email: ""
        },
        userCredentials: {
          password: crypto.SHA512(this.addUserForm.value["password"]).toString(),
          userName: this.addUserForm.value["username"]
        }
      }
      this.restClientService.registerUser(registerData).subscribe({
        complete: ()=>{
            this.statusMessage = "Successfully saved data";
            this.statusClass = StatusCssClass.SUCCESS;
        },
        error: () => {
          this.statusMessage = "Error registering user";
          this.statusClass = StatusCssClass.ERROR;
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
