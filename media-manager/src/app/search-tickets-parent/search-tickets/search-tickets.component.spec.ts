import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchTickets} from './search-tickets.component';

describe('SearchConnectionComponent', () => {
  let component: SearchTickets;
  let fixture: ComponentFixture<SearchTickets>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchTickets ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchTickets);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
