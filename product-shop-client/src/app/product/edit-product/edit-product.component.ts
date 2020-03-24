import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ProductService } from './../product.service';
import { Product } from './../delete-product/delete-product.model';
import { CategoryService } from '../../category/category.service';
import { Category } from '../../category/category-list.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  categories: Category[];
  product = new Product();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private productService: ProductService
  ) {
    this.product.categories = [];
  }

  ngOnInit(): void {
    this.categoryService.all().subscribe(data => {
      if (data) {
        this.categories = data;
      }
    });

    const id: string = this.route.snapshot.paramMap.get('id');
    this.productService.getEditDeleteDetailsModel(id).subscribe(data => {
      if (data) {
        this.product = data;
      }
    });
  }

  editProduct() {
    this.productService.editProduct(this.product).subscribe(() => {
      this.router.navigate([`/products/details/${this.product.id}`]);
    });
  }
}
