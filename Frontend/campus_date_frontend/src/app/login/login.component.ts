import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../chat_app/interfaces/login-request';
import { UserService } from '../chat_app/services/user.service';
import { ApiResponse } from '../chat_app/interfaces/api-response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  // Boolean flag to indicate whether a user was not found during login
  userNotFound: boolean = false;

  emailNotFount : boolean = false;
  passwrong : boolean = false;

  constructor(private userService: UserService, private formBuilder:FormBuilder,private http:HttpClient,private router:Router){
        // Check if the user is already logged in, and if so, redirect to the chat page
     /* if (localStorage.getItem('user') != null) {
        this.router.navigate(['chat']);
      }
        */

  }

  loginDataForm = this.formBuilder.group({
    email:[''],
    password:[''],
  });


    loginForm(): void {
      this.emailNotFount=false;
      this.passwrong=false;
      let body: LoginRequest = {
        email: this.loginDataForm.value.email as string,
        password:this.loginDataForm.value.password as string
      };
      // Call the userLogin method from the UserService and handle the response
      this.userService.userLogin(body).subscribe((res: ApiResponse) => {
        if (res.data != null && res.status!='Failed') {
          // User is successfully logged in; store user data in local storage and navigate to the chat page
          localStorage.setItem('user', JSON.stringify(res.data));
          this.router.navigate(['chat']);
        } else {
          if(res.reason=='Email not found'){
            this.emailNotFount = true;
          }else if(res.reason=='Password is wrong'){
            this.passwrong = true;
          }else{
            // User not found; set the 'userNotFound' flag to display an error message
            this.userNotFound = true;
          }
        }
      });
    }
}
