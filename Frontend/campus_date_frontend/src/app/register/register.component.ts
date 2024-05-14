import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})

export class RegisterComponent{
  hide = true;
  
  constructor(private formBuilder:FormBuilder,private http:HttpClient,private router:Router){}

  profileForm = this.formBuilder.group({
    name:[''],
    email:[''],
    university:[''],
    faculty:[''],
    address:[''],
    dob:[''],
    gender:[''],
    password:[''],
  });
 
  saveForm(){
    let bodyData = {
      "studentName" : this.profileForm.value.name,
      "email" : this.profileForm.value.email,
      "university": this.profileForm.value.university,
      "faculty" : this.profileForm.value.faculty,
      "address" : this.profileForm.value.address,
      "gender" : this.profileForm.value.gender,
      "date_of_birth" : this.profileForm.value.dob,
      "interest_gender" : "",
      "interest_age_limit" : "",
      "interest_distric" : "",
      "images" : [],
      "password" : this.profileForm.value.password
    };

    this.http.post("http://localhost:8085/api/v1/student/save",bodyData).subscribe((resultData: any)=>
      {
        if (resultData.message == "Already Registered")
          {
            alert("Email is Already Registered");
          }
          else 
          {
            alert("You Successfully Registered");
            this.router.navigateByUrl('/home');
          }
      });
  
  }
 
 }