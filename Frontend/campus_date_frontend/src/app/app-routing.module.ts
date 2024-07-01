import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth.guard';
import { NotFoundComponent } from './chat_app/components/not-found/not-found.component';
import { ChatComponent } from './chat_app/components/chat/chat.component';

const routes: Routes = [
  {path: '',component: LoginComponent,},
  {path: 'home',component: HomeComponent,},
  {path: 'register',component: RegisterComponent,},
  {
     // Route for the chat page, protected by the AuthGuard
     path: 'chat',
     canActivate: [AuthGuard],
     component: ChatComponent,
  },
   // Wildcard route to handle any undefined routes, displaying the NotFoundComponent
  { path: '**', component: NotFoundComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
