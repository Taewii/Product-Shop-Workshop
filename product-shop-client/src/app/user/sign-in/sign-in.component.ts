import { AuthService } from './../../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  signInForm;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.signInForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
  }

  signIn(data) {
    this.authService.login(data.username, data.password);
    this.router.navigate(['/']);
  }
}
