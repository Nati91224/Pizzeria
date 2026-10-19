import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private http = inject(HttpClient);
  private base = '/bff/productos';

  listar() {
    return this.http.get<any[]>(`${this.base}/listar`);
  }

  crear(producto: any) {
    return this.http.post<any>(`${this.base}/crear`, producto);
  }

  eliminar(id: number) {
    return this.http.delete<void>(`${this.base}/eliminar/${id}`);
  }
}