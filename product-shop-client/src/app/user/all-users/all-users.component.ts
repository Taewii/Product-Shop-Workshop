import { Component, OnInit } from '@angular/core';

import { User } from '../user.model';
import { User as AuthUser } from './../user.model';
import { AuthService } from './../../auth/auth.service';
import { UserService } from './../user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users: User[];
  currentUser: AuthUser;

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {
    this.currentUser = this.authService.currentUserValue;
  }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(data => {
      if (data) {
        this.users = data;
      }
    });
  }

  changeRole(newRole: string, userId: string) {
    this.userService.changeRole(userId, newRole).subscribe(() => {
      this.ngOnInit();
    });
  }
}
