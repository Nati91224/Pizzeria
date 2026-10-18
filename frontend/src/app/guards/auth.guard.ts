import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.isLoggedIn()) {
    return router.createUrlTree(['/login']);
  }

  const requeridos: string[] = route.data?.['roles'] ?? [];
  if (requeridos.length === 0) return true;

  const rol = (auth.getRole() ?? '').replace(/^ROLE_/, '');
  return requeridos.includes(rol) ? true : router.createUrlTree(['/']);
};
