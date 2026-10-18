import { Component, inject } from '@angular/core';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  private auth = inject(AuthService);
  private router = inject(Router);

  logueado() { return this.auth.isLoggedIn(); }
  rol() { return this.auth.getRole(); }

  salir() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}