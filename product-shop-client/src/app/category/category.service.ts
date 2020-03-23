import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_URL } from '../constants';
import { Category } from './category-list.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<Category[]>(`${API_URL}/categories/all`);
  }

  create(formData) {
    return this.http.post(`${API_URL}/categories/add`, { ...formData });
  }

  getEditDetails(id: number) {
    return this.http.get<Category>(`${API_URL}/categories/edit/${id}`);
  }

  editCategory(category) {
    return this.http.patch(`${API_URL}/categories/edit`, category);
  }

  getDeleteDetails(id: number) {
    return this.http.get<Category>(`${API_URL}/categories/delete/${id}`);
  }

  deleteCategory(name) {
    return this.http.delete(`${API_URL}/categories/delete/${name}`);
  }
}
