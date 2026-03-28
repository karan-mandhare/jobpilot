import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  form: FormGroup;
  loading = false;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toast: ToastService
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  fillDemo() {
    this.form.patchValue({ email: 'demo@jobpilot.com', password: 'demo123' });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.authService.login(this.form.value).subscribe({
      next: () => {
        this.toast.success('Welcome back!');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.toast.error(err.error?.message || 'Login failed');
        this.loading = false;
      }
    });
  }
}