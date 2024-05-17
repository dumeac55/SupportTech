import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewDto } from '../model/review-dto';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  async getReviewByIdTechnician(id: number) : Promise <ReviewDto | undefined > {
    return await this.http.get<ReviewDto>(this.URL + 'review?idTechnicianProfile='+id).toPromise();
  }

  async getAvgReviewByIdTehnician(id:number) : Promise< String | undefined> {
    return await this.http.get<String>(this.URL+'review/'+id+'/grade').toPromise();
  }
}
