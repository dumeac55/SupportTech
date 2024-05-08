import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SignInServiceService {

  constructor() { }
  isLogged : Boolean = false;

  public getIsLogged() : Boolean{
    return this.isLogged;
  }
  public setIsLogged(isLogged:Boolean){
    this.isLogged = isLogged;
  }
}
