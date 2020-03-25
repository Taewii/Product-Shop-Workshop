import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { OrderService } from './../order.service';
import { Order } from './order.model';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {

  order = new Order();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService
  ) { }

  ngOnInit(): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.orderService.getOrder(id).subscribe(data => {
      if (data) { this.order = data; }
    });
  }

  createOrder() {
    this.orderService.order(this.order).subscribe(data => {
      // @ts-ignore
      const id = data.message;
      this.router.navigate([`/orders/details/${id}`]);
    });
  }
}
