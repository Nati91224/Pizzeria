import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map } from "rxjs/operators";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private readonly API = "/auth";
  private tokenKey = "pizzeria_token";

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<string> {
    return this.http.post<{ token: string }>(`${this.API}/login`, { username, password })
      .pipe(map(resp => {
        const token = resp.token;
        this.setToken(token);
        return token;
      }));
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getRole(): string | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      const payload = token.split('.')[1];
      const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
      const claims = JSON.parse(decodeURIComponent(escape(json)));
      return claims.rol || claims.role || null;
    } catch (e) {
      console.warn("AuthService: error decoding token", e);
      return null;
    }
  }

  getRoles(): string[] {
    const token = this.getToken();
    if (!token) return [];
    try {
      const payload = token.split('.')[1];
      const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
      const claims = JSON.parse(decodeURIComponent(escape(json)));
      if (Array.isArray(claims.roles)) return claims.roles;
      if (Array.isArray(claims.authorities)) return claims.authorities;
      return claims.rol ? [claims.rol] : (claims.role ? [claims.role] : []);
    } catch {
      return [];
    }
  }
}
