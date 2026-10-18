import { Routes } from '@angular/router';
import { NuevoPedido } from './nuevo-pedido/nuevo-pedido';
import { Productos } from './productos/productos';
import { LoginComponent } from './login/login.component';
import { PanelPedidos } from './panel-pedidos/panel-pedidos';
import { roleGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: NuevoPedido },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    component: Productos,
    canActivate: [roleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'cocina',
    component: PanelPedidos,
    canActivate: [roleGuard],
    data: {
      roles: ['COCINERO', 'ADMIN'],
      titulo: 'Cocina: pedidos por preparar',
      estadoActual: 'SOLICITADO',
      estadoSiguiente: 'PREPARADO',
      textoBoton: 'Marcar como preparado'
    }
  },
  {
    path: 'reparto',
    component: PanelPedidos,
    canActivate: [roleGuard],
    data: {
      roles: ['REPARTIDOR', 'ADMIN'],
      titulo: 'Reparto: pedidos por entregar',
      estadoActual: 'PREPARADO',
      estadoSiguiente: 'ENTREGADO',
      textoBoton: 'Marcar como entregado'
    }
  },
  { path: '**', redirectTo: '' }
];