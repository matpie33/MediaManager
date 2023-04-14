import { Component } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormBuilder} from "@angular/forms";
import {MOCKED_PROFILE_DATA} from "./data/profileData";
import {ProfileSaveService} from "./profile-save.service";

@Component({
  selector: 'app-profile-management',
  templateUrl: './profile-management.component.html',
  styleUrls: ['./profile-management.component.css']
})
export class ProfileManagementComponent {
  constructor(private formBuilder:FormBuilder, private profileSave: ProfileSaveService) {
  }

  profileData = MOCKED_PROFILE_DATA;

  profileForm: FormGroup = this.formBuilder.group({
    firstName: this.profileData.firstName,
    lastName: this.profileData.lastName,
    email: this.profileData.email,
  });

  showStatus = false;
  saveStatus: string = "";

  onSubmitProfile() {
    let profileData = {
      firstName: this.profileForm.controls["firstName"].value,
      lastName: this.profileForm.controls["lastName"].value,
      email: this.profileForm.controls["email"].value,
    }
    this.saveStatus = "Saving data...";
    this.showStatus = true;
    this.profileSave.saveUser(profileData).subscribe(isSaved=> {
      if (isSaved){
        this.showStatus = true;
        this.saveStatus = "Data has been successfully saved!";
        setTimeout(()=> this.showStatus = false, 3000);
      }
    });
    this.profileForm.markAsPristine();
  }

}
