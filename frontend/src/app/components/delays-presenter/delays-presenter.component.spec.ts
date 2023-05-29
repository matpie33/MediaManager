import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelaysPresenterComponent } from './delays-presenter.component';

describe('DelaysPresenterComponent', () => {
  let component: DelaysPresenterComponent;
  let fixture: ComponentFixture<DelaysPresenterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DelaysPresenterComponent]
    });
    fixture = TestBed.createComponent(DelaysPresenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
