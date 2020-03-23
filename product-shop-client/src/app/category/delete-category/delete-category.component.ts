import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { CategoryService } from './../category.service';
import { Category } from '../category-list.model';

@Component({
  selector: 'app-delete-category',
  templateUrl: './delete-category.component.html',
  styleUrls: ['./delete-category.component.css']
})
export class DeleteCategoryComponent implements OnInit {

  category: Category = new Category();
  id: number;

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.categoryService.getDeleteDetails(this.id).subscribe(data => {
      if (data) {
        this.category = data;
      }
    });
  }

  deleteCategory(): void {
    this.categoryService.deleteCategory(this.category.name).subscribe(() => {
      this.router.navigate(['/categories/all']);
    });
  }
}
