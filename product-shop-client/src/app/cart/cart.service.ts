import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_URL } from '../constants';
import { Order } from './cart-navbar.model';
import { Order as OrderDetails } from './cart-details/cart-details.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  getCartItems() {
    return this.http.get<Order[]>(`${API_URL}/cart/orders`);
  }

  getCartDetails() {
    return this.http.get<OrderDetails[]>(`${API_URL}/cart/details`);
  }

  removeItem(id: string) {
    return this.http.delete(`${API_URL}/cart/remove/${id}`);
  }

  checkout() {
    return this.http.post(`${API_URL}/cart/checkout`, {});
  }
}
