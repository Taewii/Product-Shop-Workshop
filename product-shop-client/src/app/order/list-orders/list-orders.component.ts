import { Order } from './list-order.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-list-orders',
  templateUrl: './list-orders.component.html',
  styleUrls: ['./list-orders.component.css']
})
export class ListOrdersComponent implements OnInit {

  @Input() title: string;
  @Input() orders: Order[];

  constructor() { }

  ngOnInit(): void {
  }
}
