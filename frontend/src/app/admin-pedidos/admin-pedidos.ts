import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PedidoService } from '../services/pedido';

@Component({
  selector: 'app-admin-pedidos',
  imports: [FormsModule],
  template: `
    <section class="panel">
      <h2>Todos los pedidos</h2>

      <div class="barra">
        <label>
          Filtrar:
          <select (change)="filtro.set($any($event.target).value)">
            <option value="">Todos</option>
            @for (e of estados; track e) {
              <option [value]="e">{{ e }}</option>
            }
          </select>
        </label>
        <button (click)="cargar()">Actualizar</button>
      </div>

      @if (mensaje()) {
        <p class="msg">{{ mensaje() }}</p>
      }
      @if (visibles().length === 0) {
        <p>No hay pedidos para mostrar.</p>
      }

      @if (editando(); as e) {
        <div class="card">
          <h3>Editar pedido #{{ e.id }}</h3>
          <input [(ngModel)]="e.cliente" placeholder="Cliente" />
          <input [(ngModel)]="e.direccionEntrega" placeholder="Dirección" />
          <input type="number" [(ngModel)]="e.total" placeholder="Total" />
          <div class="acciones">
            <button (click)="guardar()">Guardar</button>
            <button (click)="cancelarEdicion()">Cerrar</button>
          </div>
        </div>
      }

      @for (p of visibles(); track p.id) {
        <article class="card" [class]="'card ' + p.estado">
          <div class="cabecera">
            <strong>Pedido #{{ p.id }}</strong>
            <span class="estado">{{ p.estado }}</span>
          </div>
          <span>Cliente: {{ p.cliente || 'Sin nombre' }}</span>
          <span>Dirección: {{ p.direccionEntrega || 'Sin dirección' }}</span>
          <span>Total: $ {{ p.total }}</span>

          <div class="acciones">
            <select (change)="cambiar(p.id, $any($event.target).value)">
              @for (e of estados; track e) {
                <option [value]="e" [selected]="e === p.estado">{{ e }}</option>
              }
            </select>
            <button (click)="editar(p)">Editar</button>
            @if (p.estado !== 'CANCELADO' && p.estado !== 'ENTREGADO') {
              <button (click)="cancelar(p.id)">Cancelar</button>
            }
            <button class="peligro" (click)="eliminar(p.id)">Eliminar</button>
          </div>
        </article>
      }
    </section>
  `,
  styles: [`
    .panel { max-width: 720px; margin: 24px auto; padding: 0 16px; }
    .barra { display: flex; gap: 12px; align-items: center; margin-bottom: 12px; }
    .card { display: flex; flex-direction: column; gap: 4px; border: 1px solid #ccc;
            border-radius: 8px; padding: 12px; margin: 12px 0; border-left-width: 6px; }
    .card.SOLICITADO { border-left-color: #e67e22; }
    .card.PREPARADO { border-left-color: #2980b9; }
    .card.ENTREGADO { border-left-color: #1b7a4b; }
    .card.CANCELADO { border-left-color: #999; opacity: .7; }
    .cabecera { display: flex; justify-content: space-between; }
    .estado { font-size: .8rem; font-weight: 700; }
    .acciones { display: flex; gap: 8px; margin-top: 8px; flex-wrap: wrap; }
    .peligro { color: #b00020; }
    .msg { color: #b00020; }
    input { padding: 6px 8px; margin: 2px 0; }
    button { cursor: pointer; padding: 6px 12px; }
  `]
})
export class AdminPedidos implements OnInit {
  private servicio = inject(PedidoService);

  estados = ['SOLICITADO', 'PREPARADO', 'ENTREGADO', 'CANCELADO'];
  pedidos = signal<any[]>([]);
  filtro = signal('');
  mensaje = signal('');
  editando = signal<any | null>(null);

  visibles = computed(() => {
    const f = this.filtro();
    const lista = f ? this.pedidos().filter(p => p.estado === f) : this.pedidos();
    return [...lista].sort((a, b) => b.id - a.id);
  });

  ngOnInit() {
    this.cargar();
  }

  cargar() {
    this.servicio.listar().subscribe({
      next: lista => {
        this.mensaje.set('');
        this.pedidos.set(lista);
      },
      error: () => this.mensaje.set('No se pudieron cargar los pedidos')
    });
  }

  cambiar(id: number, estado: string) {
    this.servicio.actualizarEstado(id, estado).subscribe({
      next: () => this.cargar(),
      error: e => this.mensaje.set(this.textoError(e))
    });
  }

  cancelar(id: number) {
    if (confirm(`¿Cancelar el pedido #${id}?`)) this.cambiar(id, 'CANCELADO');
  }

  eliminar(id: number) {
    if (!confirm(`¿Eliminar definitivamente el pedido #${id}? No se puede deshacer.`)) return;
    this.servicio.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: e => this.mensaje.set(this.textoError(e))
    });
  }

  editar(p: any) {
    this.editando.set({ ...p });
  }

  cancelarEdicion() {
    this.editando.set(null);
  }

  guardar() {
    const p = this.editando();
    if (!p) return;
    this.servicio.actualizar(p.id, {
      cliente: p.cliente,
      direccionEntrega: p.direccionEntrega,
      total: p.total
    }).subscribe({
      next: () => {
        this.editando.set(null);
        this.cargar();
      },
      error: e => this.mensaje.set(this.textoError(e))
    });
  }

  private textoError(e: any) {
    if (e.status === 401) return 'Tu sesión venció. Volvé a iniciar sesión.';
    if (e.status === 403) return 'Tu rol no puede hacer esta acción.';
    return 'No se pudo completar la acción.';
  }
}