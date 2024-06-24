import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtStorageService {

  constructor(private httpClient: HttpClient) { }
  public generateToken(request: any) {
    return this.httpClient.post("http://localhost:8080/api/authenticate/signin", request, { responseType: 'text' as 'json' });
  }
  public setTokenStorage( token: any, username: any): void{
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
  }

  public isTokenExpired(token: string): boolean {
    const expirationDate = this.getTokenExpirationDate(token);
    if (expirationDate === undefined) return false;
    return expirationDate < new Date();
  }

  private getTokenExpirationDate(token: string): Date | undefined {
    try {
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      if (decodedToken.exp === undefined) return undefined;
      const expirationDate = new Date(0);
      expirationDate.setUTCSeconds(decodedToken.exp);
      return expirationDate;
    } catch (error) {
      return undefined;
    }
  }
}
