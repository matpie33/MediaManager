import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageUsersParentComponent } from './manage-users-parent.component';

describe('ManageUsersParentComponent', () => {
  let component: ManageUsersParentComponent;
  let fixture: ComponentFixture<ManageUsersParentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageUsersParentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageUsersParentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
