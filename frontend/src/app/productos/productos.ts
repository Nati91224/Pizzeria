import { Component, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../services/producto';

@Component({
  selector: 'app-productos',
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './productos.html',
  styleUrl: './productos.scss'
})
export class Productos {
  private service = inject(ProductoService);

  productos = signal<any[]>([]);
  mensaje = signal('');
  nuevo = { nombre: '', descripcion: '', precio: null as number | null };

  constructor() {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe({
      next: data => this.productos.set(data),
      error: err => console.error('Error al listar productos', err)
    });
  }

  guardar() {
    if (!this.nuevo.nombre.trim() || this.nuevo.precio === null) {
      this.mensaje.set('Completá el nombre y el precio.');
      return;
    }
    this.service.crear(this.nuevo).subscribe({
      next: () => {
        this.nuevo = { nombre: '', descripcion: '', precio: null };
        this.mensaje.set('Producto creado ✔');
        this.cargar();
      },
      error: err => {
        console.error('Error al crear producto', err);
        this.mensaje.set('No se pudo crear el producto.');
      }
    });
  }
      eliminar(id: number) {
      if (!confirm('¿Eliminar esta pizza del menú?')) return;
      this.service.eliminar(id).subscribe({
        next: () => {
          this.mensaje.set('Producto eliminado ✔');
          this.cargar();
        },
        error: err => {
          console.error('Error al eliminar producto', err);
          this.mensaje.set('No se pudo eliminar el producto.');
        }
      });
    }
}