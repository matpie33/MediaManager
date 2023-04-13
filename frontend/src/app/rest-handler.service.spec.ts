import { TestBed } from '@angular/core/testing';

import { RestHandlerService } from './rest-handler.service';

describe('RestHandlerService', () => {
  let service: RestHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RestHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
