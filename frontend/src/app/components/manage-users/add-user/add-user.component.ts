import { Component } from '@angular/core';
import {ViewWithStatus} from "../../common/view-with-status";
import {UserAuthenticationService} from "../../../services/user-authentication.service";
import {RoleService} from "../../../services/role.service";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
  providers: [RoleService]
})
export class AddUserComponent extends ViewWithStatus{

  currentUserRoles = this.roleService.currentUserRoles;
  addUserForm = this.passwordService.registerForm;

  constructor(private passwordService: UserAuthenticationService, private roleService: RoleService) {
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

  userNotPossessedRoles(): Set<string> {
    return this.roleService.userNotPossessedRoles();
  }

  addRole(selectedRole: HTMLSelectElement) {
    this.roleService.addRole(selectedRole);
  }

  remove(currentRole: string) {
    this.roleService.remove(currentRole);
  }
}
