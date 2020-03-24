import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { IndexComponent } from './index/index.component';
import { SignUpComponent } from './user/sign-up/sign-up.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { AllUsersComponent } from './user/all-users/all-users.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { EditUserProfileComponent } from './user/edit-user-profile/edit-user-profile.component';
import { AuthGuard } from './auth/auth.guard';
import { GuestGuard } from './auth/guest.guard';
import { EditCategoryComponent } from './category/edit-category/edit-category.component';
import { CreateCategoryComponent } from './category/create-category/create-category.component';
import { DeleteCategoryComponent } from './category/delete-category/delete-category.component';
import { AllCategoriesComponent } from './category/all-categories/all-categories.component';
import { EditProductComponent } from './product/edit-product/edit-product.component';
import { DeleteProductComponent } from './product/delete-product/delete-product.component';
import { ProductDetailsComponent } from './product/product-details/product-details.component';
import { AllProductsComponent } from './product/all-products/all-products.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const appRoutes: Routes = [
  { path: '', pathMatch: 'full', component: IndexComponent },
  { path: 'signin', pathMatch: 'full', component: SignInComponent, canActivate: [GuestGuard] },
  { path: 'signup', pathMatch: 'full', component: SignUpComponent, canActivate: [GuestGuard] },
  {
    path: 'categories', canActivate: [AuthGuard], data: { role: 'Moderator' },
    children: [
      { path: 'all', component: AllCategoriesComponent },
      { path: 'add', component: CreateCategoryComponent },
      { path: 'delete/:id', component: DeleteCategoryComponent },
      { path: 'edit/:id', component: EditCategoryComponent },
    ]
  },
  {
    path: 'users', canActivate: [AuthGuard], data: { role: 'User' },
    children: [
      { path: 'all', component: AllUsersComponent, canActivate: [AuthGuard], data: { role: 'Admin' } },
      { path: 'profile', component: UserProfileComponent },
      { path: 'edit', component: EditUserProfileComponent },
    ]
  },
  {
    path: 'products',
    children: [
      { path: 'details/:id', component: ProductDetailsComponent },
      { path: 'edit/:id', component: EditProductComponent, canActivate: [AuthGuard], data: { role: 'Moderator' } },
      { path: 'delete/:id', component: DeleteProductComponent, canActivate: [AuthGuard], data: { role: 'Moderator' } },
      { path: 'add', component: CreateProductComponent, canActivate: [AuthGuard], data: { role: 'Moderator' } },
      { path: 'all', component: AllProductsComponent, canActivate: [AuthGuard], data: { role: 'Moderator' } }
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {
        enableTracing: false, // <-- debugging purposes only
      }
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
