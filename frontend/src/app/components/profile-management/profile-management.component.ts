import { Component } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormBuilder} from "@angular/forms";
import {RestHandlerService} from "../../services/rest-handler.service";
import {LoginConstants} from "../login/data/login-enums";

@Component({
  selector: 'app-profile-management',
  templateUrl: './profile-management.component.html',
  styleUrls: ['./profile-management.component.css']
})
export class ProfileManagementComponent {

  profileForm: FormGroup = this.formBuilder.group({
    firstName: "",
    lastName: "",
    email: "",
  });

  showStatus = false;
  saveStatus: string = "";

  constructor(private formBuilder:FormBuilder, private restHandler: RestHandlerService) {
    restHandler.getUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe(
      result => {
        this.profileForm.controls["firstName"].setValue(result.firstName);
        this.profileForm.controls["lastName"].setValue(result.lastName);
        this.profileForm.controls["email"].setValue(result.email);
      }
    );
  }

  onSubmitProfile() {
    let profileData = {
      firstName: this.profileForm.controls["firstName"].value,
      lastName: this.profileForm.controls["lastName"].value,
      email: this.profileForm.controls["email"].value,
    }
    this.saveStatus = "Saving data...";
    this.showStatus = true;
    this.restHandler.editUser(profileData, Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe({
      next: this.handleEditPersonalDataDone.bind(this)
    });
    this.profileForm.markAsPristine();
  }

  handleEditPersonalDataDone (){
    this.showStatus = true;
    this.saveStatus = "Data has been successfully saved!";
    setTimeout(()=> this.showStatus = false, 3000);
  }

}
