import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private formBuilder:FormBuilder,private http:HttpClient,private router:Router){}

  loginDataForm = this.formBuilder.group({
    email:[''],
    password:[''],
  });

 
  loginForm() {
 
    let bodyData = {
      "email" : this.loginDataForm.value.email,
      "password" : this.loginDataForm.value.password
    };
 
        this.http.post("http://localhost:8085/api/v1/student/login", bodyData).subscribe(  (resultData: any) => {
 
        if (resultData.message == "Email not exits")
        {
          alert("Email not exits");
        }
        else if(resultData.message == "Login Success")
        {
          this.router.navigateByUrl('/home');
        }
        else
        {
          alert("Incorrect Email and Password not match");
        }
      });
    }
}
