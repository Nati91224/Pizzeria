import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private http = inject(HttpClient);
  
  // Reemplaza 8080 por el puerto donde está corriendo tu Gateway o servicio de productos
  private base = 'http://localhost:8085/bff/productos'; 

  listar() {
    return this.http.get<any[]>(`${this.base}/listar`);
  }

  crear(producto: any) {
    return this.http.post<any>(`${this.base}/crear`, producto);
  }
}