import { Component, inject } from "@angular/core";
import { Router } from "@angular/router";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthService } from "../services/auth.service";

@Component({
  selector: "app-login",
  imports: [ReactiveFormsModule],
  templateUrl: "./login.component.html"
})
export class LoginComponent {
  private auth = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  form = this.fb.group({
    username: ["", Validators.required],
    password: ["", Validators.required]
  });
  error: string | null = null;
  loading = false;

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = null;
    const { username, password } = this.form.value;

    this.auth.login(username!, password!).subscribe({
      next: () => {
        this.loading = false;
        const rol = (this.auth.getRole() ?? "").replace(/^ROLE_/, "");
        if (rol === "ADMIN") this.router.navigate(["/admin"]);
        else if (rol === "COCINERO") this.router.navigate(["/cocina"]);
        else if (rol === "REPARTIDOR") this.router.navigate(["/reparto"]);
        else this.router.navigate(["/"]);
      },
      error: () => {
        this.loading = false;
        this.error = "Usuario o contraseña incorrectos";
      }
    });
  }
}