import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpForm;

  constructor(
    private formBuilder: FormBuilder
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
    console.log(data);
    this.signUpForm.reset();
  }
}
