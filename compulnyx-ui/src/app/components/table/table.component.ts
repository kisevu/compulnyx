import {CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { StudentsService } from '../../services/students/students.service';

@Component({
  selector: 'app-table',
  imports: [CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent  implements OnInit{
  students!: any [];
  studentService = inject(StudentsService);

  ngOnInit(): void {
    this.studentService.retrieveStudents().subscribe({
      next: (data) => {
        this.students =data;
        console.log('Students data:', data);
      },
      error: (err) => {
        console.error('Error retrieving students:', err);
      }
    });
  }

}
