import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { CategoryService } from './../../category/category.service';
import { Category } from '../../category/category-list.model';
import { ProductService } from '../product.service';
import { Product } from './delete-product.model';

@Component({
  selector: 'app-delete-product',
  templateUrl: './delete-product.component.html',
  styleUrls: ['./delete-product.component.css']
})
export class DeleteProductComponent implements OnInit {

  private id: string;
  product = new Product();
  categories: Category[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private categoryService: CategoryService
  ) {
    this.product.categories = [];
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    this.categoryService.all().subscribe(data => {
      if (data) {
        this.categories = data;
      }
    });

    this.productService.getEditDeleteDetailsModel(this.id).subscribe(data => {
      if (data) {
        this.product = data;
      }
    });
  }

  deleteProduct() {
    this.productService.deleteProduct(this.id).subscribe(() => {
      this.router.navigate(['/products/all']);
    });
  }
}
