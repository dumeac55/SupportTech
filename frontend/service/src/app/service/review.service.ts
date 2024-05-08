import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewDto } from '../model/review-dto';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  async getReviewByIdMechanic(id: number) : Promise <ReviewDto | undefined > {
    return await this.http.get<ReviewDto>(this.URL + 'review?idMechanicProfile='+id).toPromise();
  }

  async getAvgReviewByIdMechaic(id:number) : Promise< String | undefined> {
    return await this.http.get<String>(this.URL+'review/'+id+'/grade').toPromise();
  }
}
