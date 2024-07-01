import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiResponse } from '../chat_app/interfaces/api-response';
import { Student } from '../chat_app/interfaces/student';
import { UserService } from '../chat_app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})

export class RegisterComponent{
  hide = true;
  
  constructor(private userService: UserService, private formBuilder:FormBuilder,private http:HttpClient,private router:Router){}

  profileForm = this.formBuilder.group({
    first_Name:[''],
    second_Name:[''],
    email:[''],
    university:[''],
    faculty:[''],
    address:[''],
    dob:[''],
    gender:[''],
    password:[''],
  });
 

  saveForm(): void {
    let body: Student = {
      studentId : 0,
      first_Name : this.profileForm.value.first_Name as string,
      last_Name : this.profileForm.value.second_Name as string,
      email : this.profileForm.value.email as string,
      university: this.profileForm.value.university as string,
      faculty : this.profileForm.value.faculty as string,
      address : this.profileForm.value.address as string,
      gender : this.profileForm.value.gender as string,
      date_of_birth : this.profileForm.value.dob as string,
      password : this.profileForm.value.password as string
    };
    // Call the userRegister method from the UserService and handle the response
    this.userService.userRegister(body).subscribe((res: ApiResponse) => {
      if (res.data != null) {
        // User is successfully registered; store user data in local storage and navigate to the chat page
        localStorage.setItem('user', JSON.stringify(res.data));
        this.router.navigate(['chat']);
      } else {
        // Registration failed
        console.log(res);
      }
    });
  }
 
 }