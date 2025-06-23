import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { StudentsService } from '../../services/students/students.service';
import { Student } from '../../models/Student';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-student-management',
  imports: [FormsModule, AsyncPipe, ReactiveFormsModule],
  templateUrl: './student-management.component.html',
  styleUrl: './student-management.component.css'
})
export class StudentManagementComponent implements OnInit {
  studentService = inject(StudentsService);

  $students!: Observable<Student[]>;
  students: Student[] = [];
  formMode: 'add' | 'edit' = 'add';
  selectedStudent!: Student;
  studentForm!: FormGroup;
  selectedFile: File | null = null;
  modalVisible = false;

  constructor(private fb:FormBuilder, private http:HttpClient) {}
  private readonly apiUrl = environment.studentsUrl;

  ngOnInit(): void {
    this.$students = this.studentService.retrieveStudents();
    // this.loadStudents();
    this.initForm();
  }

   initForm() {
    this.studentForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      dob: [''],
      className: [''],
      score: [0]
    });
  }

  loadStudents() {
    this.http.get<Student[]>(`${this.apiUrl}/all-students`)
      .subscribe(data => this.students = data);
  }

    addStudent() {
    this.formMode = 'add';
    this.studentForm.reset();
    this.selectedFile = null;
    this.modalVisible = true;
  }

  editStudent(student: Student) {
    this.formMode = 'edit';
    this.selectedStudent = student;
    this.studentForm.patchValue(student);
    this.selectedFile = null;
    this.modalVisible = true;
  }

  viewStudent(student: Student) {
    this.selectedStudent = student;
    alert(`Student: ${student.firstName} ${student.lastName}\nClass: ${student.className}`);
  }

  deleteStudent(student: Student) {
    if (confirm(`Are you sure you want to delete ${student.firstName} ${student.lastName}?`)) {
      this.http.delete(`${this.apiUrl}/${student.id}`)
        .subscribe(() => this.loadStudents());
    }
  }


  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (!file) return;

    const allowedTypes = ['image/png', 'image/jpeg'];
    if (!allowedTypes.includes(file.type)) {
      alert('Only PNG and JPEG allowed.');
      return;
    }

    if (file.size > 5 * 1024 * 1024) {
      alert('Max size is 5MB.');
      return;
    }

    this.selectedFile = file;
  }

  submitForm() {
    const formData = new FormData();
    formData.append('student', new Blob([JSON.stringify(this.studentForm.value)], {
      type: 'application/json'
    }));

    if (this.selectedFile) {
      formData.append('photo', this.selectedFile);
    }
    if (this.formMode === 'add') {
      this.http.post(`${this.apiUrl}`, formData)
        .subscribe(() => {
          this.modalVisible = false;
          this.loadStudents();
        });
    } else {
      this.http.put(`${this.apiUrl}/all-students/${this.selectedStudent.id}`, formData)
        .subscribe(() => {
          this.modalVisible = false;
          this.loadStudents();
        });
    }
  }


}
