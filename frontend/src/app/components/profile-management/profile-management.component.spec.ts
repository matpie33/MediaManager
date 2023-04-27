
import { ProfileManagementComponent } from './profile-management.component';
import {LoginConstants} from "../login/data/login-enums";
import {RestHandlerService} from "../../services/rest-handler.service";
import {of} from "rxjs";
import {FormBuilder} from "@angular/forms";

describe('ProfileManagementComponent', () => {
  let restServiceSpy: any;
  let profileManagementComponent: ProfileManagementComponent;
  let userData: any;

  beforeEach(async () => {
    let restHandlerServicePrototype = RestHandlerService.prototype;
    restServiceSpy = jasmine.createSpyObj(RestHandlerService.name, [restHandlerServicePrototype.editUser.name,
      restHandlerServicePrototype.getUser.name]);
    restServiceSpy.editUser.and.returnValue(of(1));
    sessionStorage.clear();
    userData = {
      firstName: "John",
      lastName: "Connor",
      email: "john.connor@gmail.com"
    };
    restServiceSpy.getUser.and.returnValue(of(userData))
    profileManagementComponent = new ProfileManagementComponent(new FormBuilder(), restServiceSpy);
  });

  it('should edit user data and show success message', () => {
    sessionStorage.setItem(LoginConstants.USER_ID, "1");
    profileManagementComponent.onSubmitProfile();
    expect(restServiceSpy.editUser).toHaveBeenCalledWith(userData, 1);
    expect( profileManagementComponent.saveStatus ).toBe("Data has been successfully saved!");

  });
});
