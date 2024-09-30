import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule , Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms'; // <-- Import this
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { OperationsComponent } from './operations/operations.component';
import { canActivate } from './services/auth-guard.service';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { PatientsComponent } from './patients/patients.component';
import { TeammemberComponent } from './teammember/teammember.component';
import { OperationtypeComponent } from './operationtype/operationtype.component';
import { AssessmentComponent } from './assessment/assessment.component';
import { RoominventoryComponent } from './roominventory/roominventory.component';
import { OperationroomComponent } from './operationroom/operationroom.component';


const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'operations', component: OperationsComponent , canActivate: [canActivate]},
  {path: 'patients', component: PatientsComponent , canActivate: [canActivate]},
  {path: 'teamMembers', component: TeammemberComponent , canActivate: [canActivate]},
  {path: 'operationTypes', component: OperationtypeComponent , canActivate: [canActivate]},
  {path: 'assessments', component: AssessmentComponent , canActivate: [canActivate]},
  {path: 'roomInventory', component: RoominventoryComponent , canActivate: [canActivate]},
  {path: 'operationRoom', component: OperationroomComponent , canActivate: [canActivate]}
]



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    OperationsComponent,
    PatientsComponent,
    TeammemberComponent,
    OperationtypeComponent,
    AssessmentComponent,
    RoominventoryComponent,
    OperationroomComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
