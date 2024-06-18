import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JwtDialogComponent } from './jwt-dialog.component';

describe('JwtDialogComponent', () => {
  let component: JwtDialogComponent;
  let fixture: ComponentFixture<JwtDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JwtDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(JwtDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
