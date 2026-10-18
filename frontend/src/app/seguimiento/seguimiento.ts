
import {
  Component,
  Input,
  OnDestroy,
  OnInit,
  inject,
  signal
} from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PedidoService } from '../services/pedido';

@Component({
  selector: 'app-seguimiento',
  imports: [RouterLink],
  template: `
    <section class="seg">
      <h3>Estado de tu pedido #{{ id }}</h3>

      @if (cargando()) {
        <p>Consultando el estado del pedido...</p>
      } @else if (noExiste()) {
        <p class="aviso">No encontramos ese pedido.</p>
      } @else {
        <ol class="pasos">
          @for (p of pasos; track p.estado; let i = $index) {
            <li [class.activo]="i <= indice()">
              <span>{{ i + 1 }}</span>
              {{ p.texto }}
            </li>
          }
        </ol>

        @if (estado() === 'ENTREGADO') {
          <p class="ok">¡Tu pedido fue entregado! Que lo disfrutes.</p>
        } @else {
          <p class="nota">El estado se actualiza automáticamente.</p>
        }
      }

      @if (mostrarVolver) {
        <a routerLink="/">Volver al inicio</a>
      }
    </section>
  `,
  styles: [`
    .seg {
      max-width: 520px;
      margin: 24px auto 0;
      padding: 16px;
      text-align: left;
      background: #fffdf7;
      border-radius: 14px;
    }

    h3 {
      color: var(--verde);
      text-align: center;
      margin-top: 0;
    }

    .pasos {
      list-style: none;
      padding: 0;
      display: flex;
      flex-direction: column;
      gap: 10px;
    }

    .pasos li {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 12px;
      border: 2px solid #d9d4c3;
      border-radius: 10px;
      color: #777;
    }

    .pasos li span {
      display: grid;
      place-items: center;
      min-width: 30px;
      height: 30px;
      border-radius: 50%;
      background: #d9d4c3;
      color: white;
      font-weight: bold;
    }

    .pasos li.activo {
      background: var(--verde);
      border-color: var(--verde);
      color: white;
    }

    .pasos li.activo span {
      background: white;
      color: var(--verde);
    }

    .ok {
      font-weight: bold;
      color: var(--verde);
    }

    .aviso {
      color: #b00020;
    }

    .nota {
      color: #666;
      font-size: .9rem;
    }
  `]
})
export class Seguimiento implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private servicio = inject(PedidoService);

  @Input() pedidoId: number | null = null;
  @Input() mostrarVolver = true;

  id = 0;

  pasos = [
    { estado: 'SOLICITADO', texto: 'Recibimos tu pedido' },
    { estado: 'PREPARADO', texto: 'Tu pizza está lista' },
    { estado: 'ENTREGADO', texto: 'Pedido entregado' }
  ];

  estado = signal('SOLICITADO');
  noExiste = signal(false);
  cargando = signal(true);

  indice = () =>
    this.pasos.findIndex(p => p.estado === this.estado());

  private timer: ReturnType<typeof setInterval> | undefined;

  ngOnInit() {
    const idParam =
      this.pedidoId !== null
        ? String(this.pedidoId)
        : this.route.snapshot.paramMap.get('id');

    const id = Number(idParam);

    if (!idParam || !Number.isInteger(id) || id <= 0) {
      this.cargando.set(false);
      this.noExiste.set(true);
      return;
    }

    this.id = id;
    this.consultar();

    this.timer = setInterval(() => this.consultar(), 5000);
  }

  ngOnDestroy() {
    if (this.timer !== undefined) {
      clearInterval(this.timer);
    }
  }

  private consultar() {
    this.servicio.seguimiento(this.id).subscribe({
      next: r => {
        this.cargando.set(false);

        const estadoValido = this.pasos.some(
          p => p.estado === r.estado
        );

        if (!estadoValido) {
          console.error('Estado de pedido desconocido:', r.estado);
          return;
        }

        this.noExiste.set(false);
        this.estado.set(r.estado);

        if (r.estado === 'ENTREGADO' && this.timer !== undefined) {
          clearInterval(this.timer);
        }
      },
      error: e => {
        this.cargando.set(false);

        if (e.status === 404) {
          this.noExiste.set(true);

          if (this.timer !== undefined) {
            clearInterval(this.timer);
          }
        } else {
          console.error('Error al consultar el seguimiento', e);
        }
      }
    });
  }
}
