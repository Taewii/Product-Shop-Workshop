import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { JwtInterceptor } from './auth/jwt.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { GuestNavbarComponent } from './navbar/guest-navbar/guest-navbar.component';
import { UserNavbarComponent } from './navbar/user-navbar/user-navbar.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { SignUpComponent } from './user/sign-up/sign-up.component';
import { AllUsersComponent } from './user/all-users/all-users.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { EditUserProfileComponent } from './user/edit-user-profile/edit-user-profile.component';
import { AllCategoriesComponent } from './category/all-categories/all-categories.component';
import { DeleteCategoryComponent } from './category/delete-category/delete-category.component';
import { CreateCategoryComponent } from './category/create-category/create-category.component';
import { EditCategoryComponent } from './category/edit-category/edit-category.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { AllProductsComponent } from './product/all-products/all-products.component';
import { ProductDetailsComponent } from './product/product-details/product-details.component';
import { DeleteProductComponent } from './product/delete-product/delete-product.component';
import { EditProductComponent } from './product/edit-product/edit-product.component';

@NgModule({
  declarations: [
    AppComponent,
    GuestNavbarComponent,
    UserNavbarComponent,
    NavbarComponent,
    PageNotFoundComponent,
    IndexComponent,
    SignInComponent,
    SignUpComponent,
    AllCategoriesComponent,
    DeleteCategoryComponent,
    CreateCategoryComponent,
    EditCategoryComponent,
    AllUsersComponent,
    UserProfileComponent,
    EditUserProfileComponent,
    CreateProductComponent,
    AllProductsComponent,
    ProductDetailsComponent,
    DeleteProductComponent,
    EditProductComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
