import { Component, OnInit, OnDestroy, inject, signal } from '@angular/core';
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
  <span>Teléfono: {{ p.telefono || 'Sin teléfono' }}</span>
  <span>Dirección: {{ p.direccionEntrega || 'Sin dirección' }}</span>

  @if (p.items?.length) {
    <ul class="items">
      @for (i of p.items; track $index) {
        <li>{{ i.cantidad }} x {{ i.nombre }}</li>
      }
    </ul>
  }

  @if (p.notas) {
    <span class="notas">Notas: {{ p.notas }}</span>
  }

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
    .items { margin: 4px 0; padding-left: 20px; }
.notas { font-style: italic; color: #8a5a00; }
  `]
})
export class PanelPedidos implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private servicio = inject(PedidoService);

  titulo = '';
  estadoActual = '';
  estadoSiguiente = '';
  textoBoton = '';

  pedidos = signal<any[]>([]);
  mensaje = signal('');
  private timer: any;

  ngOnInit() {
  const d = this.route.snapshot.data;
  this.titulo = d['titulo'];
  this.estadoActual = d['estadoActual'];
  this.estadoSiguiente = d['estadoSiguiente'];
  this.textoBoton = d['textoBoton'];
  this.cargar();
  this.timer = setInterval(() => this.cargar(), 10000);
}

ngOnDestroy() {
  clearInterval(this.timer);
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