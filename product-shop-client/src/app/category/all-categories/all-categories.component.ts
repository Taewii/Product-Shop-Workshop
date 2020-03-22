import { Component, OnInit } from '@angular/core';

import { CategoryService } from './../category.service';
import { Category } from '../category-list.model';

@Component({
  selector: 'app-all-categories',
  templateUrl: './all-categories.component.html',
  styleUrls: ['./all-categories.component.css']
})
export class AllCategoriesComponent implements OnInit {

  categories: Category[];

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.all().subscribe((categories: Category[]) => {
      if (categories) {
        this.categories = categories
      }
    });
  }
}
