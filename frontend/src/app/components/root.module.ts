import {NgModule} from '@angular/core';
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
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { PageNotFoundComponent } from './error-handling/page-not-found/page-not-found.component';
import {AuthenticationGuardAdmin} from "./login/authentication/authentication-guard-admin";
import { AccessDeniedComponent } from './error-handling/access-denied/access-denied.component';
import { AddConnectionComponent } from './add-connection/add-connection.component';
import {EditUsersComponent} from "./manage-users/edit-users/edit-users.component";
import { ManageUsersParentComponent } from './manage-users/manage-users-parent/manage-users-parent.component';
import { AddUserComponent } from './manage-users/add-user/add-user.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { QrCodeScannerComponent } from './qr-code-scanner/qr-code-scanner.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import { HeaderComponent } from './navigation/header/header.component';
import { FlexLayoutModule} from "@angular/flex-layout";
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { ProgressSpinnerComponent } from './progress-spinner/progress-spinner.component';
import {CacheInterceptorService} from "../services/cache-interceptor.service";


const appRoutes: Routes = [
  {path: MenuItems.SEARCH_CONNECTIONS.toString(), component: SearchTicketsParentComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.TICKETS_MANAGEMENT.toString(), component: TicketsManagementComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.PROFILE.toString(), component: ProfileManagementComponent, canActivate: [AuthenticationGuard]},
  {path: MenuItems.NEWS.toString(), component: NewsComponent},
  {path: MenuItems.ADD_CONNECTION.toString(), component: AddConnectionComponent, canActivate: [AuthenticationGuardAdmin]},
  {path: MenuItems.MANAGE_USERS.toString(), component: ManageUsersParentComponent, canActivate: [AuthenticationGuardAdmin]},
  {path: MenuItems.SCAN_QR_CODE.toString(), component: QrCodeScannerComponent, canActivate: [AuthenticationGuardAdmin]},
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
    AddUserComponent,
    QrCodeScannerComponent,
    HeaderComponent,
    SidenavListComponent,
    ProgressSpinnerComponent
  ],
  imports: [
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatToolbarModule,
    MatSidenavModule,
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes
    ),
    HttpClientModule,
    ZXingScannerModule,
    BrowserAnimationsModule,
    FlexLayoutModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: CacheInterceptorService, multi: true}],
  bootstrap: [RootComponent]
})
export class RootModule { }
