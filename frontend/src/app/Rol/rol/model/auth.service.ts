import { Injectable } from '@angular/core';
import { Rol } from '../models/rol.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  // Guarda el rol recibido tras el login
  setRol(rol: string): void {
    localStorage.setItem('user_role', rol);
  }

  // Obtiene el rol actual
  getRol(): string | null {
    return localStorage.getItem('user_role');
  }

  // Verifica si el usuario tiene un rol específico
  hasRol(rolEsperado: Rol): boolean {
    const rolActual = this.getRol();
    return rolActual === rolEsperado;
  }

  // Cierra sesión
  logout(): void {
    localStorage.removeItem('user_role');
  }
}