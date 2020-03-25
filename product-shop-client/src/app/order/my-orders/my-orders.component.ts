import { Component, OnInit } from '@angular/core';

import { OrderService } from '../order.service';
import { Order } from './../list-orders/list-order.model';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  title = 'My Orders';
  orders: Order[];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.myOrders().subscribe(data => {
      if (data) { this.orders = data; }
    });
  }
}
