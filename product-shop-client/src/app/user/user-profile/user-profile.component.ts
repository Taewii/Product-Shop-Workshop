import { Component, OnInit } from '@angular/core';

import { User } from './user-profile.model';
import { UserService } from './../user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User = new User();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(data => {
      if (data) {
        this.user = data;
      }
    });
  }
}
