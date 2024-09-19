import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/authService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: '../app.component.css'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required, Validators.minLength(2)]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(2)]),
    });
  }

  onSubmit(): void {
    console.log(this.loginForm.value.email);
    console.log(this.loginForm.value.password);

    this.authService.login(this.loginForm.value.email, this.loginForm.value.password).subscribe(
      response => {
        console.log('Success', response);
        this.loginForm.reset();
      },
      error => {
        console.error('Error', error);
      }
    )
  }
}


