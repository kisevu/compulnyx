import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-generate-excel',
  imports: [CommonModule,FormsModule],
  templateUrl: './generate-excel.component.html',
  styleUrl: './generate-excel.component.css'
})
export class GenerateExcelComponent  implements OnInit{

  recordCount: number = 0;

  private readonly apiUrl = environment.documentsUrl;

  constructor(private http:HttpClient ){}

  ngOnInit(): void {
  }

  onGenerate(): void {
    const url = `${this.apiUrl}/generate?count=${this.recordCount}`;

    this.http.get(url, { responseType: 'blob' }).subscribe({
      next: (blob) => {
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = `students_${this.recordCount}_records.xlsx`;
        link.click();
      },
      error: (err) => {
        console.error('Excel generation failed:', err);
      }
    });
  }





}
