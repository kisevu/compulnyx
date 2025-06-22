import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentReportAndExportComponent } from './student-report-and-export.component';

describe('StudentReportAndExportComponent', () => {
  let component: StudentReportAndExportComponent;
  let fixture: ComponentFixture<StudentReportAndExportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentReportAndExportComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentReportAndExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
