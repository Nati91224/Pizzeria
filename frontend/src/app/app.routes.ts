import { Routes } from '@angular/router';
import { NuevoPedido } from './nuevo-pedido/nuevo-pedido';
import { Productos } from './productos/productos';

export const routes: Routes = [
  { path: '', component: NuevoPedido },
  { path: 'admin', component: Productos },
  { path: '**', redirectTo: '' }
];