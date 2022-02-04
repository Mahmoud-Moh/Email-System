import {Router, RouterModule, Routes} from "@angular/router";
import {homeComponent} from "./pages/home/home.component";
import {mailComponent} from "./pages/mail/mail.component";
import {loginComponent} from "./pages/login/login.component";
import {signupComponent} from "./pages/signup/signup.component";

const routes: Routes = [
  {path: '', component: signupComponent},
  {path: 'home', component: homeComponent},
  {path: 'home/:folder', component: homeComponent},
  {path: 'mail', component: mailComponent},
  {path: 'mail/:id', component: mailComponent},
  {path: '**', redirectTo:''}
];

export const appRoutingModule = RouterModule.forRoot(routes);
