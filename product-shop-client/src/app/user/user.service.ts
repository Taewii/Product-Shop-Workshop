import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_URL } from '../constants';
import { User } from './user.model';

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
}
