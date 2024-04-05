import { Injectable } from '@angular/core';
import { UserProfileDto } from '../model/user-profile-dto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  private URL = 'http://localhost:8080/api/';
  private userProfile?: UserProfileDto;
  private id?: number;

  constructor(private http:HttpClient) { }

  async getUserProfile(): Promise <UserProfileDto | undefined>{
    this.id = await this.getUserIdByUserName(localStorage.getItem('username'));
  
    return await this.http.get<UserProfileDto>(this.URL + "user/" + this.id).toPromise();
  }
  
  async getUserIdByUserName(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "user/username=" + username).toPromise();
  }
}
