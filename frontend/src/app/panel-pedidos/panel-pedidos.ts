import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PedidoService } from '../services/pedido';

@Component({
  selector: 'app-panel-pedidos',
  template: `
    <section class="panel">
      <h2>{{ titulo }}</h2>
      <button class="actualizar" (click)="cargar()">Actualizar</button>

      @if (mensaje()) {
        <p class="msg">{{ mensaje() }}</p>
      }
      @if (pedidos().length === 0) {
        <p>No hay pedidos para mostrar.</p>
      }

      @for (p of pedidos(); track p.id) {
        <article class="card">
          <strong>Pedido #{{ p.id }}</strong>
          <span>Cliente: {{ p.cliente || 'Sin nombre' }}</span>
          <span>Direccion: {{ p.direccionEntrega || 'Sin direccion' }}</span>
          <span>Total: $ {{ p.total }}</span>
          <button (click)="avanzar(p.id)">{{ textoBoton }}</button>
        </article>
      }
    </section>
  `,
  styles: [`
    .panel { max-width: 640px; margin: 24px auto; padding: 0 16px; }
    .card { display: flex; flex-direction: column; gap: 4px; border: 1px solid #ccc;
            border-radius: 8px; padding: 12px; margin: 12px 0; }
    .msg { color: #b00020; }
    button { cursor: pointer; padding: 8px 12px; }
  `]
})
export class PanelPedidos implements OnInit {
  private route = inject(ActivatedRoute);
  private servicio = inject(PedidoService);

  titulo = '';
  estadoActual = '';
  estadoSiguiente = '';
  textoBoton = '';

  pedidos = signal<any[]>([]);
  mensaje = signal('');

  ngOnInit() {
    const d = this.route.snapshot.data;
    this.titulo = d['titulo'];
    this.estadoActual = d['estadoActual'];
    this.estadoSiguiente = d['estadoSiguiente'];
    this.textoBoton = d['textoBoton'];
    this.cargar();
  }

  cargar() {
    this.servicio.listar().subscribe({
      next: lista => {
        this.mensaje.set('');
        this.pedidos.set(lista.filter(p => p.estado === this.estadoActual));
      },
      error: () => this.mensaje.set('No se pudieron cargar los pedidos')
    });
  }

  avanzar(id: number) {
    this.servicio.actualizarEstado(id, this.estadoSiguiente).subscribe({
      next: () => this.cargar(),
      error: e => this.mensaje.set(
        e.status === 403 ? 'Tu rol no puede hacer este cambio' : 'No se pudo actualizar el pedido'
      )
    });
  }
}