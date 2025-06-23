import { Component, inject } from '@angular/core';
import { DocumentService } from '../../services/document/document.service';

@Component({
  selector: 'app-file-upload',
  imports: [],
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.css'
})
export class FileUploadComponent {

  documentService = inject(DocumentService);

   selectedFile: File | null = null;

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadSelectedFile() {
    if (this.selectedFile) {
      this.documentService.uploadExcel(this.selectedFile).subscribe({
        next: (msg) => alert("Upload successful!"),
        error: (err) => alert("Upload failed: " + err.error)
      });
    } else {
      alert("Please select a file first.");
    }
  }

}
