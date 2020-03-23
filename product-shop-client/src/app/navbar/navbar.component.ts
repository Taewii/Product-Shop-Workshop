import { Component, OnInit } from '@angular/core';

import { AuthService } from './../auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn: boolean;
  isAdmin: boolean;
  isModerator: boolean;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.isLoggedIn = !!user;

      if (user) {
        this.isModerator = user.roles.includes('MODERATOR');
        this.isAdmin = user.roles.includes('ADMIN');
      }
    });
  }
}
