import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProductService } from '../product.service';
import { Product } from './product-details.model';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  product = new Product();
  id: string;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.productService.getDetailsModel(this.id).subscribe(data => {
      if (data) {
        this.product = data;
      }
    });
  }
}
