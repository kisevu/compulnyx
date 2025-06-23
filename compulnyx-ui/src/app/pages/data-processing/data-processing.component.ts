import { Component, inject } from '@angular/core';
import { DocumentService } from '../../services/document/document.service';

@Component({
  selector: 'app-data-processing',
  imports: [],
  templateUrl: './data-processing.component.html',
  styleUrl: './data-processing.component.css'
})
export class DataProcessingComponent {

  documentService = inject(DocumentService);

  processExcelToCsv() {
    this.documentService.processAndDownloadCsv().subscribe(blob => {
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(blob);
      a.href = objectUrl;
      a.download = 'processed_students.csv';
      a.click();
      URL.revokeObjectURL(objectUrl);
    }, error => {
      alert("Something went wrong while processing the file.");
      console.error(error);
    });
  }
}
