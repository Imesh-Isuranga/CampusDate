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
  emailAlreadyExists : boolean = false;
  enterfirstname : boolean = false;
  entersecondname : boolean = false;
  enteremail : boolean = false;
  enteruniversity : boolean = false;
  enterfaculty : boolean = false;
  enteraddress : boolean = false;
  enterdob : boolean = false;
  enterpassword : boolean = false;

  allcorrect : boolean = true;
  
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
    this.emailAlreadyExists = false;
    this.enterfirstname = false;
    this.entersecondname = false;
    this.enteremail = false;
    this.enteruniversity = false;
    this.enterfaculty = false;
    this.enteraddress = false;
    this.enterdob = false;
    this.enterpassword = false;
    this.allcorrect = true;
    this.emailAlreadyExists = false;

    if(this.profileForm.value.first_Name==''){
      this.enterfirstname = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.second_Name==''){
      this.entersecondname = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.email==''){
      this.enteremail = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.university==''){
      this.enteruniversity = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.faculty==''){
      this.enterfaculty = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.address==''){
      this.enteraddress = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.dob==''){
      this.enterdob = true;
      this.allcorrect = false;
    }
    
    if(this.profileForm.value.password==''){
      this.enterpassword = true;
      this.allcorrect = false;
    }
    
    if(this.allcorrect == true) {
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
        if (res.data != null || res.status!='Failed') {
          // User is successfully registered; store user data in local storage and navigate to the chat page
          localStorage.setItem('user', JSON.stringify(res.data));
          this.router.navigate(['chat']);
        } else {
          this.emailAlreadyExists = true;
        }
      });
    }
  }
 
 }