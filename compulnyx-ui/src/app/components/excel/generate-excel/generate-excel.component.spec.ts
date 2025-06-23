import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateExcelComponent } from './generate-excel.component';

describe('GenerateExcelComponent', () => {
  let component: GenerateExcelComponent;
  let fixture: ComponentFixture<GenerateExcelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateExcelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateExcelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
