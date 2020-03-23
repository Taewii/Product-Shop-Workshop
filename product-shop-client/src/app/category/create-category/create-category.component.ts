import { FormBuilder } from '@angular/forms';
import { CategoryService } from './../category.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.css']
})
export class CreateCategoryComponent implements OnInit {

  createCategoryForm;

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.createCategoryForm = this.formBuilder.group({ name: '' });
  }

  ngOnInit(): void {
  }

  createCategory(data) {
    this.categoryService.create(data).subscribe(() => {
      this.router.navigate(['/categories/all']);
    });
  }
}
