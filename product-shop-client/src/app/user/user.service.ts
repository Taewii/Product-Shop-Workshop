import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_URL } from '../constants';
import { User } from './user.model';
import { User as UserProfile } from './user-profile/user-profile.model';
import { EditProfile } from './edit-user-profile/edit-profile.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  signUp(formValues) {
    return this.http.post(`${API_URL}/auth/signup`, { ...formValues });
  }

  getAllUsers() {
    return this.http.get<User[]>(`${API_URL}/users/all`);
  }

  changeRole(id: string, newRole: string) {
    return this.http.post(`${API_URL}/users/set-${newRole}/${id}`, {});
  }

  getUserProfile() {
    return this.http.get<UserProfile>(`${API_URL}/users/profile`);
  }

  editProfile(profile: EditProfile) {
    return this.http.patch(`${API_URL}/users/edit`, profile);
  }
}
