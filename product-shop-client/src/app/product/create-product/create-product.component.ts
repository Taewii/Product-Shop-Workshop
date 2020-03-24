import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CategoryService } from './../../category/category.service';
import { Category } from '../../category/category-list.model';
import { ProductService } from '../product.service';
import { ProductBindingModel } from './product-binding-model.model';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

  categories: Category[];
  product = new ProductBindingModel();
  formData = new FormData();

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private router: Router

  ) { }

  ngOnInit(): void {
    this.categoryService.all().subscribe(data => {
      if (data) {
        this.categories = data;
      }
    });
  }

  onFileSelect(event) {
    this.formData.append('image', event.target.files[0]);
  }

  createProduct() {
    this.formData.append('name', this.product.name);
    this.formData.append('description', this.product.description);
    this.formData.append('price', this.product.price);
    this.formData.append('categories', this.product.categories);

    this.productService.createProduct(this.formData).subscribe(data => {
      // @ts-ignore
      const id = data.message;
      this.router.navigate([`/products/details/${id}`]);
    });
  }
}
