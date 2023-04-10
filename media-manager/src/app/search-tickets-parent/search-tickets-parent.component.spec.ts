import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchTicketsParentComponent} from './search-tickets-parent.component';

describe('SearchTicketsParentComponent', () => {
  let component: SearchTicketsParentComponent;
  let fixture: ComponentFixture<SearchTicketsParentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchTicketsParentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchTicketsParentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
