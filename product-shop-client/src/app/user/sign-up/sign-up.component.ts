import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from './../user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpForm;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.signUpForm = this.formBuilder.group({
      username: '',
      password: '',
      confirmPassword: '',
      email: ''
    });
  }

  ngOnInit(): void {
  }

  signUp(data) {
    this.userService.signUp(data).subscribe();
    this.signUpForm.reset();
    this.router.navigate(['signin']);
  }
}
