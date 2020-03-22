import { AuthService } from './auth/auth.service';
import { AllCategoriesComponent } from './category/all-categories/all-categories.component';
import { GuestGuard } from './auth/guest.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SignUpComponent } from './user/sign-up/sign-up.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { IndexComponent } from './index/index.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from './auth/auth.guard';

const appRoutes: Routes = [
  { path: '', pathMatch: 'full', component: IndexComponent },
  { path: 'signin', pathMatch: 'full', component: SignInComponent, canActivate: [GuestGuard] },
  { path: 'signup', pathMatch: 'full', component: SignUpComponent, canActivate: [GuestGuard] },
  { path: 'categories/all', component: AllCategoriesComponent, canActivate: [AuthGuard], data: { role: 'Moderator' }},
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
