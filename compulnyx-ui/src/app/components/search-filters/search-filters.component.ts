import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Student } from '../../models/Student';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-search-filters',
  imports: [CommonModule,FormsModule],
  templateUrl: './search-filters.component.html',
  styleUrl: './search-filters.component.css'
})
export class SearchFiltersComponent {

  http = inject(HttpClient);

  private readonly apiUrl = environment.studentsUrl;

page = 0;
size = 10;
students: Student[] = [];
classOptions = ['Class1', 'Class2', 'Class3', 'Class4', 'Class5'];

filters = {
  studentId: '',
  className: '',
  startDob: '',
  endDob: ''
};

loadStudents() {
  const params = {
    page: this.page,
    size: this.size,
    ...this.filters
  };

  this.http.get<Student[]>(`${this.apiUrl}/reports`, { params })
    .subscribe(data => this.students = data);
}

previousPage() {
  if (this.page > 0) {
    this.page--;
    this.loadStudents();
  }
}

nextPage() {
  this.page++;
  this.loadStudents();
}

exportToExcel() {
  const params = {
    ...this.filters
  };

  this.http.get(`${this.apiUrl}/export`, {
    params,
    responseType: 'blob'
  }).subscribe(blob => {
    const a = document.createElement('a');
    a.href = window.URL.createObjectURL(blob);
    a.download = 'student-report.xlsx';
    a.click();
  });
}

}
