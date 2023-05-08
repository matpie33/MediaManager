import { Component } from '@angular/core';

@Component({
  selector: 'app-manage-users-parent',
  templateUrl: './manage-users-parent.component.html',
  styleUrls: ['./manage-users-parent.component.css']
})
export class ManageUsersParentComponent {

  manageUsersEnum: "edit" | "add" = "edit";

  switchToEdit(addButton: HTMLButtonElement, editButton: HTMLButtonElement) {
    this.manageUsersEnum = "edit";
    addButton.removeAttribute("data-chosen");
    editButton.setAttribute("data-chosen", "");
  }

  switchToAdd(addButton: HTMLButtonElement, editButton: HTMLButtonElement) {
    this.manageUsersEnum = "add";
    editButton.removeAttribute("data-chosen");
    addButton.setAttribute("data-chosen", "");
  }
}
