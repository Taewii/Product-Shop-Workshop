import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn: boolean;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.isLoggedIn = !!user;
    });
  }
}
