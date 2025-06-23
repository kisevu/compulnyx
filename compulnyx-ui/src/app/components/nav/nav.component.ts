import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-nav',
  imports: [RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {

  authService = inject(AuthService);
  router = inject(Router);

  onLogout():void {
    this.authService.logout();
    this.router.navigate(['/login']);
    }
}
