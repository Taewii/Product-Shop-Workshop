import { Component, OnInit } from '@angular/core';

import { OrderService } from '../order.service';
import { Order } from './../list-orders/list-order.model';

@Component({
  selector: 'app-all-orders',
  templateUrl: './all-orders.component.html',
  styleUrls: ['./all-orders.component.css']
})
export class AllOrdersComponent implements OnInit {

  title = 'All Orders';
  orders: Order[];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.all().subscribe(data => {
      if (data) { this.orders = data; }
    });
  }

}
