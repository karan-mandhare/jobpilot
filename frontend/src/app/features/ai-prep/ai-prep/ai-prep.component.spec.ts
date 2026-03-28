import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AiPrepComponent } from './ai-prep.component';

describe('AiPrepComponent', () => {
  let component: AiPrepComponent;
  let fixture: ComponentFixture<AiPrepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AiPrepComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AiPrepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
