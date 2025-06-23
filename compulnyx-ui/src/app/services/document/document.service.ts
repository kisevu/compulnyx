import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) { }
  private readonly apiUrl = environment.documentsUrl;

  processAndDownloadCsv(): Observable<Blob> {
  return this.http.get(`${this.apiUrl}/process`, {
    responseType: 'blob'
      });
    }

  uploadExcel(file: File): Observable<string> {
  const formData = new FormData();
  formData.append('file', file);
  return this.http.post(`${this.apiUrl}/upload/excel`, formData, {
    responseType: 'text'
      });
    }

}
