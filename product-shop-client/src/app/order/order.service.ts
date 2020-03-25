import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { API_URL } from '../constants';
import { Order } from './create-order/order.model';
import { Order as OrderList } from './list-orders/list-order.model';
import { Order as OrderDetails } from './order-details/order-details.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<OrderList[]>(`${API_URL}/orders/all`);
  }

  myOrders() {
    return this.http.get<OrderList[]>(`${API_URL}/orders/mine`);
  }

  getOrder(id: string) {
    return this.http.get<Order>(`${API_URL}/orders/product/${id}`);
  }

  getOrderDetails(id: string) {
    return this.http.get<OrderDetails>(`${API_URL}/orders/details/${id}`);
  }

  order(data: Order) {
    return this.http.post(`${API_URL}/orders/order`, data);
  }
}
