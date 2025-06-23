import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StudentsService {
  private readonly apiUrl = environment.studentsUrl;
  constructor(private http:HttpClient) { }

  retrieveStudents(): Observable<any>{
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get(`${this.apiUrl}/all-students`, { headers });
  }
}

