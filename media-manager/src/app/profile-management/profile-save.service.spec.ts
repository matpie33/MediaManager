import { TestBed } from '@angular/core/testing';

import { ProfileSaveService } from './profile-save.service';

describe('ProfileSaveService', () => {
  let service: ProfileSaveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileSaveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
