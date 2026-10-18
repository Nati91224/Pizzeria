import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

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

  actualizarEstado(id: number, estado: string) {
    const params = new HttpParams().set('estado', estado);
    return this.http.put<any>(`${this.base}/estado/${id}`, null, { params });
  }

  actualizar(id: number, pedido: any) {
    return this.http.put<any>(
      `${this.base}/actualizar/${id}`,
      pedido
    );
  }

  eliminar(id: number) {
    return this.http.delete<void>(`${this.base}/eliminar/${id}`);
  }

  seguimiento(id: number) {
    return this.http.get<{ id: number; estado: string }>(`${this.base}/seguimiento/${id}`);
  }
}