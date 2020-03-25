import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './../../auth/auth.service';
import { Order } from '../../cart/cart-navbar.model';
import { CartService } from './../../cart/cart.service';

@Component({
  selector: 'app-user-navbar',
  templateUrl: './user-navbar.component.html',
  styleUrls: ['./user-navbar.component.css']
})
export class UserNavbarComponent implements OnInit {

  @Input() isAdmin: boolean;
  @Input() isModerator: boolean;
  orders: Order[];

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cartService.getCartItems().subscribe(data => {
      if (data) { this.orders = data; }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
