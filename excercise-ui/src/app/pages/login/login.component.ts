import { Component } from '@angular/core';
import { AuthenticationRequest } from "../../services/models/"

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

authRequest: AuthenticationRequest = {email:'',password:''};
erroMsg: Array<string> = [];
  register():void{

  }
  login():void{

  }

}
