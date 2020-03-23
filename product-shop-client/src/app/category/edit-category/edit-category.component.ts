import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { CategoryService } from './../category.service';
import { Category } from '../category-list.model';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css']
})
export class EditCategoryComponent implements OnInit {

  category: Category = new Category();

  constructor(
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    const id: number = +this.route.snapshot.paramMap.get('id');
    this.categoryService.getEditDetails(id).subscribe(data => {
      if (data) {
        this.category = data;
        this.category.id = id;
      }
    });
  }

  editCategory() {
    this.categoryService.editCategory(this.category).subscribe(() => {
      this.router.navigate(['/categories/all']);
    });
  }
}
