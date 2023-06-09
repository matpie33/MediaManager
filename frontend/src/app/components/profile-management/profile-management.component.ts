import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormBuilder} from "@angular/forms";
import {RestClientService} from "../../services/rest-client.service";
import {LoginConstants} from "../login/data/login-enums";
import {ViewWithStatus} from "../common/view-with-status";

@Component({
  selector: 'app-profile-management',
  templateUrl: './profile-management.component.html',
  styleUrls: ['./profile-management.component.css']
})
export class ProfileManagementComponent extends ViewWithStatus implements OnInit{

  profileForm: FormGroup = this.formBuilder.group({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
  });
  loadingData = true;


  constructor(private formBuilder:FormBuilder, private restHandler: RestClientService) {
    super();

  }

  ngOnInit(): void {
    this.restHandler.getUser(Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe(
      result => {
        this.profileForm.controls["firstName"].setValue(result.firstName);
        this.profileForm.controls["lastName"].setValue(result.lastName);
        this.profileForm.controls["email"].setValue(result.email);
        this.profileForm.controls["phoneNumber"].setValue(result.phoneNumber);
        this.loadingData = false;
      }
    );
    }

  onSubmitProfile() {
    let profileData = {
      firstName: this.profileForm.controls["firstName"].value,
      lastName: this.profileForm.controls["lastName"].value,
      email: this.profileForm.controls["email"].value,
      phoneNumber: this.profileForm.controls["phoneNumber"].value,
    }
    this.showInfoMessage("Saving data...");
    this.restHandler.editUser(profileData, Number.parseInt(sessionStorage.getItem(LoginConstants.USER_ID)!)).subscribe({
      next: this.handleEditPersonalDataDone.bind(this)
    });
    this.profileForm.markAsPristine();
  }

  handleEditPersonalDataDone (){
    this.showSuccessMessage("Data has been successfully saved!");
    this.hideStatusAfterDelay();
  }

}
