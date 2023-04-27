import { TestBed } from '@angular/core/testing';

import { RestHandlerService } from './rest-handler.service';
import {provideHttpClient} from "@angular/common/http";

describe('RestHandlerService', () => {
  let service: RestHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({providers: [provideHttpClient()]});
    service = TestBed.inject(RestHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
