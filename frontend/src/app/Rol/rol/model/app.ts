import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Rol } from './rol/rol.model'; // o el nombre exacto de tu archivo dentro de la carpeta rol
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent implements OnInit {

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Ejemplo: Si simulas el login o lo recibes de tu backend
    // this.authService.setRol('ADMIN');
  }

  // Método que usará el HTML
  esAdmin(): boolean {
    return this.authService.hasRol(Rol.ADMIN);
  }
}