import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { authGuard } from './guard/auth.guard';
import { DataGenerationComponent } from './pages/data-generation/data-generation.component';
import { DataProcessingComponent } from './pages/data-processing/data-processing.component';
import { FileUploadComponent } from './pages/file-upload/file-upload.component';
import { StudentManagementComponent } from './pages/student-management/student-management.component';
import { StudentReportAndExportComponent } from './pages/student-report-and-export/student-report-and-export.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full' ,
  },

  {
     path: 'login',
    component: LoginComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'data-generation',
        component: DataGenerationComponent
      },
      {
        path: 'data-processing',
        component: DataProcessingComponent
      },
      {
        path: 'file-upload',
        component: FileUploadComponent
      },
      {
        path: 'student-management',
        component: StudentManagementComponent
      },
      {
        path: 'report-and-export',
        component: StudentReportAndExportComponent
      }
    ]
  },

   {
    path: '**',
    redirectTo: 'login'
   }
];
