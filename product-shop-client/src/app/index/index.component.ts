import { Component, OnInit } from '@angular/core';

import { AuthService } from './../auth/auth.service';
import { ProductService } from './../product/product.service';
import { Product } from './../product/all-products/product-list.model';
import { CategoryService } from '../category/category.service';
import { Category } from '../category/category-list.model';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  isLoggedIn: boolean;
  categories: Category[];
  products: Product[];

  constructor(
    private authService: AuthService,
    private categoryService: CategoryService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.authService.currentUser.subscribe(data => {
      this.isLoggedIn = !!data;

      if (this.isLoggedIn) {
        this.categoryService.all().subscribe(categories => {
          if (data) { this.categories = categories; }
        });

        this.productService.getAll().subscribe(products => {
          if (data) { this.products = products; }
        });
      }
    });
  }

  getProductsByCategory(categoryId) {
    this.productService.getAllByCategory(categoryId).subscribe(data => {
      if (data) { this.products = data; }
    });
  }
}
