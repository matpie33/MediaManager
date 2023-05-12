import { Injectable } from '@angular/core';
import {Role} from "../constants/role";

@Injectable()
export class RoleService {
  currentUserRoles = new Set<string>();
  readonly NO_ROLE = "none";
  roleTypes = Role;

  constructor() {
    this.currentUserRoles.add(this.NO_ROLE);
  }

  remove(currentRole: string) {
    this.currentUserRoles.delete(currentRole);
    if (this.currentUserRoles.size == 0){
      this.currentUserRoles.add(this.NO_ROLE);
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
    if (this.currentUserRoles.has(this.NO_ROLE)){
      this.currentUserRoles.clear();
    }
    this.currentUserRoles.add(selectedRole.value);
  }
}
