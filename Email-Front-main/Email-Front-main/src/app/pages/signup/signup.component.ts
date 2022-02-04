import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {ApiService} from "../../Service/api.service";
import {Account} from "../../Account/Account";
import {Result} from "../../Result";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class signupComponent implements OnInit {
  constructor(private router: Router, private http: HttpClient) {

  }

  ngOnInit(): void {
  }

  user = "";
  email = "";
  pass = "";
  backResponse !: Result;
  error_message = "";

  signup_request() {
    if (!(this.user == "" || this.email == "" || this.pass == "")) {
      console.log("hello world");
      new ApiService(this.http).signUpPost({
          "UserName": this.user,
          "Pass": this.pass,
          "EmailAddress": this.email
        }
      ).subscribe(response => {
        this.backResponse = response;
        if (response.result == -2)
          this.error_message = "Account already exists, please log in.";
        else if (response.result == -1)
          this.error_message = "Error happened, please try again.";
        else if (response.result != undefined) {
          AppComponent.currentAccount = new Account(this.user, this.email, this.pass, 0);
          AppComponent.currentAccount.ID = response.result;
          console.log("Our account details are", AppComponent.currentAccount.ID, AppComponent.currentAccount.EmailAddress);
          this.router.navigate(['/home']).then();
        } else {
          console.log("Error, really big error.");
        }
      })
    }
  }

  login_request(): void {
    console.log("TICK",this.email);
    this.http.get<Result>('http://localhost:8080/email/login', {
      params: {
        email: this.email,
        password: this.pass
      },
      observe: 'response'
    }).subscribe(response => {
      if(response != null) {
        AppComponent.currentAccount = new Account("dummy", this.email, this.pass, response.body!.result);
        if (response.body?.result == -1 || response.body?.result == -2) return;
        this.router.navigate(['/home/inbox']).then();
      }else
        console.log("response was null");
    })
  }

  getSignupAccount() {
    return AppComponent.currentAccount;
  }
}
