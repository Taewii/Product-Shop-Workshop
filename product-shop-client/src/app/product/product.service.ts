import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Product as ProductDetails } from './product-details/product-details.model';
import { Product as ProductDeleteDetails } from './delete-product/delete-product.model';
import { Product } from './all-products/product-list.model';
import { API_URL } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Product[]>(`${API_URL}/products/all`);
  }

  getAllByCategory(categoryId: number) {
    return this.http.get<Product[]>(`${API_URL}/products/${categoryId}`);
  }

  getDetailsModel(id: string) {
    return this.http.get<ProductDetails>(`${API_URL}/products/details/${id}`);
  }

  getEditDeleteDetailsModel(id: string) {
    return this.http.get<ProductDeleteDetails>(`${API_URL}/products/delete/${id}`);
  }

  createProduct(formData) {
    return this.http.post(`${API_URL}/products/add`, formData);
  }

  editProduct(product) {
    return this.http.patch(`${API_URL}/products/edit`, product);
  }

  deleteProduct(id: string) {
    return this.http.delete(`${API_URL}/products/delete/${id}`);
  }
}
