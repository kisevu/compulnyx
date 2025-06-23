import {CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { StudentsService } from '../../services/students/students.service';
import { FormsModule } from '@angular/forms';
import { GenerateExcelComponent } from "../excel/generate-excel/generate-excel.component";

@Component({
  selector: 'app-table',
  imports: [CommonModule, FormsModule, GenerateExcelComponent],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent  implements OnInit{
  students!: any [];
  studentService = inject(StudentsService);

  recordCount!:number;

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
