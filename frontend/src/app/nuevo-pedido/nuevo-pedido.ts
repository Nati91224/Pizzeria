import { Component, computed, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../services/producto';
import { PedidoService } from '../services/pedido';

@Component({
  selector: 'app-nuevo-pedido',
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './nuevo-pedido.html',
  styleUrl: './nuevo-pedido.scss'
})
export class NuevoPedido {
  private productoService = inject(ProductoService);
  private pedidoService = inject(PedidoService);

  paso = signal(1);
  productos = signal<any[]>([]);
  cantidades = signal<Record<number, number>>({});
  error = signal('');
  enviando = signal(false);
  confirmado = signal(false);

  cliente = { nombre: '', telefono: '', direccion: '', notas: '' };

  items = computed(() =>
    this.productos()
      .filter(p => (this.cantidades()[p.id] ?? 0) > 0)
      .map(p => ({ producto: p, cantidad: this.cantidades()[p.id] }))
  );

  total = computed(() =>
    this.items().reduce((suma, i) => suma + i.producto.precio * i.cantidad, 0)
  );

  constructor() {
    this.productoService.listar().subscribe({
      next: data => this.productos.set(data),
      error: err => console.error('Error al listar productos', err)
    });
  }

  cantidad(id: number) {
    return this.cantidades()[id] ?? 0;
  }

  cambiar(id: number, delta: number) {
    const nueva = Math.max(0, this.cantidad(id) + delta);
    this.cantidades.update(c => ({ ...c, [id]: nueva }));
  }

  irAPizzas() {
    const c = this.cliente;
    if (!c.nombre.trim() || !c.telefono.trim() || !c.direccion.trim()) {
      this.error.set('Completá nombre, teléfono y dirección.');
      return;
    }
    this.error.set('');
    this.paso.set(2);
  }

  irAResumen() {
    if (this.items().length === 0) {
      this.error.set('Elegí al menos una pizza.');
      return;
    }
    this.error.set('');
    this.paso.set(3);
  }

  volver() {
    this.error.set('');
    this.paso.update(p => p - 1);
  }

  confirmar() {
    this.enviando.set(true);
    this.error.set('');

    // Si tu PedidoDTO usa otros nombres de campo, se ajusta solo acá.
    const pedido = {
      cliente: this.cliente.nombre,
  telefono: this.cliente.telefono,
  direccionEntrega: this.cliente.direccion,
  notas: this.cliente.notas,
  total: this.total(),
  items: this.items().map(i => ({
    productoId: i.producto.id,
    nombre: i.producto.nombre,
    cantidad: i.cantidad,
    precioUnitario: i.producto.precio
  }))
    };

    this.pedidoService.crear(pedido).subscribe({
      next: () => {
        this.enviando.set(false);
        this.confirmado.set(true);
      },
      error: err => {
        console.error('Error al crear pedido', err);
        this.enviando.set(false);
        this.error.set('No se pudo enviar el pedido. Probá de nuevo.');
      }
    });
  }

  nuevoPedido() {
    this.cliente = { nombre: '', telefono: '', direccion: '', notas: '' };
    this.cantidades.set({});
    this.confirmado.set(false);
    this.paso.set(1);
  }
}