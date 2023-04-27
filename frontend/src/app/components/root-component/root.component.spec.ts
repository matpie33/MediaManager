import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {RootComponent} from './root.component';
import {RestHandlerService} from "../../services/rest-handler.service";
import {of} from "rxjs";
import {MenuItems} from "../../constants/menu-items";

describe('RootComponent', () => {
  let restServiceSpy: any;
  let fixture: ComponentFixture<RootComponent>;
  beforeEach(async () => {
    restServiceSpy = jasmine.createSpyObj(RestHandlerService.name, [RestHandlerService.prototype.getUserPermissions.name]);

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        RootComponent
      ],
      providers: [
        {provide: RestHandlerService, useValue: restServiceSpy}
      ]
    }).compileComponents();
     fixture = TestBed.createComponent(RootComponent);

  });

  it('should be able to access menu for normal user', () => {
    restServiceSpy.getUserPermissions.and.returnValue(of ({
      permissions: ["USER_ACTIVITIES"],
      id: 1
    }));
    fixture.detectChanges();

    expect(fixture.componentInstance.userAccessibleMenu).toContain(MenuItems.NEWS);
    expect(fixture.componentInstance.userAccessibleMenu).toContain(MenuItems.SEARCH_CONNECTIONS);
    expect(fixture.componentInstance.userAccessibleMenu).toContain(MenuItems.TICKETS_MANAGEMENT);
    expect(fixture.componentInstance.userAccessibleMenu).toContain(MenuItems.PROFILE);
  });

});
