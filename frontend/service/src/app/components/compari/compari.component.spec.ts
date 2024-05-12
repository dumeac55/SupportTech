import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompariComponent } from './compari.component';

describe('CompariComponent', () => {
  let component: CompariComponent;
  let fixture: ComponentFixture<CompariComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompariComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompariComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
