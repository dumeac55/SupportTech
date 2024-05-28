import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { ForumComponent } from './components/forum/forum.component';
import { CompariComponent } from './components/compari/compari.component';
import { TypeComponent } from './type/type.component';

const routes: Routes = [
  { path: 'login', component: SignInComponent },
  { path: 'register', component: SignUpComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'appointment', component: AppointmentComponent },
  { path: 'forum', component: ForumComponent },
  { path: 'forum/:id', component: ForumComponent },
  { path: 'compari', component: CompariComponent },
  { path: 'type', component: TypeComponent },
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
