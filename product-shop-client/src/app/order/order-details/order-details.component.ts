import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Order } from './order-details.model';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  order = new Order();

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService) { }

  ngOnInit(): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.orderService.getOrderDetails(id).subscribe(data => {
      if (data) { this.order = data; }
    });
  }
}
