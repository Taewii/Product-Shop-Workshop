import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from './product-list.model';

@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.css']
})
export class AllProductsComponent implements OnInit {

  products: Product[];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAll().subscribe(data => {
      if (data) {
        this.products = data;
      }
    });
  }
}
