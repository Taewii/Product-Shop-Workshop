import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Order } from './cart-details.model';
import { CartService } from './../cart.service';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

  orders: Order[];
  total: number;

  constructor(
    private cartService: CartService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cartService.getCartDetails().subscribe(data => {
      if (data) {
        this.orders = data;
        this.total = data.reduce((acc, cur) => acc += cur.productPrice * cur.quantity, 0);
      }
    });
  }

  removeOrder(id: string) {
    this.cartService.removeItem(id).subscribe(() => {
      this.orders = this.orders.filter(x => x.id !== id);
    });
  }

  checkOut() {
    this.cartService.checkout().subscribe(() => {
      this.router.navigate(['/orders/mine']);
    });
  }
}
