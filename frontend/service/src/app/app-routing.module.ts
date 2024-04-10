import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AppointmentComponent } from './components/appointment/appointment.component';

const routes: Routes = [
  {path: 'login', component:SignInComponent},
  {path: 'register', component:SignUpComponent},
  {path: 'profile', component:ProfileComponent},
  {path: 'appointment', component:AppointmentComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
