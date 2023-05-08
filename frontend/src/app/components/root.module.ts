import {inject, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {RootComponent} from './root-component/root.component';
import {SearchTicketsComponent} from './search-tickets-parent/search-tickets/search-tickets.component';
import {RouterModule, Routes} from "@angular/router";
import {MenuItems} from "../constants/menu-items";
import {ReactiveFormsModule} from "@angular/forms";
import {TicketSummaryComponent} from './search-tickets-parent/ticket-summary/ticket-summary.component';
import {SearchTicketsParentComponent} from './search-tickets-parent/search-tickets-parent.component';
import { TicketsManagementComponent } from './tickets-management/tickets-management.component';
import { LoginComponent } from './login/login.component';
import { TicketsListComponent } from './tickets-management/tickets-list/tickets-list.component';
import { ProfileManagementComponent } from './profile-management/profile-management.component';
import { NewsComponent } from './news/news.component';
import {AuthenticationGuard} from "./login/authentication/authentication-guard";
import {LoginConstants} from "./login/data/login-enums";
import {HttpClientModule} from "@angular/common/http";
import { PageNotFoundComponent } from './error-handling/page-not-found/page-not-found.component';
import {AuthenticationGuardAdmin} from "./login/authentication/authentication-guard-admin";
import { AccessDeniedComponent } from './error-handling/access-denied/access-denied.component';
import { AddConnectionComponent } from './add-connection/add-connection.component';
import {EditUsersComponent} from "./manage-users/edit-users/edit-users.component";
import { ManageUsersParentComponent } from './manage-users/manage-users-parent/manage-users-parent.component';
import { AddUserComponent } from './manage-users/add-user/add-user.component';

const appRoutes: Routes = [
  {path: MenuItems.SEARCH_CONNECTIONS.toString(), component: SearchTicketsParentComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.TICKETS_MANAGEMENT.toString(), component: TicketsManagementComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.PROFILE.toString(), component: ProfileManagementComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.NEWS.toString(), component: NewsComponent},
  {path: MenuItems.ADD_CONNECTION.toString(), component: AddConnectionComponent, canActivate: [AuthenticationGuardAdmin]},
  {path: MenuItems.MANAGE_USERS.toString(), component: ManageUsersParentComponent, canActivate: [AuthenticationGuardAdmin]},
  {path: LoginConstants.LOGIN_URL.toString(), component: LoginComponent},
  {path: LoginConstants.ACCESS_DENIED.toString(), component: AccessDeniedComponent},
  {path: '', redirectTo: MenuItems.NEWS, pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent},
  ]

@NgModule({
  declarations: [
    RootComponent,
    SearchTicketsComponent,
    TicketSummaryComponent,
    SearchTicketsParentComponent,
    TicketsManagementComponent,
    LoginComponent,
    TicketsListComponent,
    ProfileManagementComponent,
    NewsComponent,
    PageNotFoundComponent,
    AccessDeniedComponent,
    AddConnectionComponent,
    EditUsersComponent,
    ManageUsersParentComponent,
    AddUserComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes
    ),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [RootComponent]
})
export class RootModule { }
