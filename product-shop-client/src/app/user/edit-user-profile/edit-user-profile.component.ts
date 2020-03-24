import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { EditProfile } from './edit-profile.model';
import { User } from './../user-profile/user-profile.model';
import { UserService } from './../user.service';

@Component({
  selector: 'app-edit-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.css']
})
export class EditUserProfileComponent implements OnInit {

  user: User = new User();
  editProfileModel: EditProfile = new EditProfile();

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(data => {
      if (data) {
        this.user = data;
        this.editProfileModel.email = this.user.email;
      }
    });
  }

  editProfile() {
    this.userService.editProfile(this.editProfileModel).subscribe(() => {
      this.router.navigate(['/users/profile']);
    });
  }
}
