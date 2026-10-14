import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class PedidoService {
  private http = inject(HttpClient);
  private base = '/bff/pedidos';

  listar() {
    return this.http.get<any[]>(`${this.base}/listar`);
  }

  crear(pedido: any) {
    return this.http.post<any>(`${this.base}/crear`, pedido);
  }
}