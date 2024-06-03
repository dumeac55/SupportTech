import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewDto } from '../model/review-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private URL = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  getReviewByIdTechnician(id: number) : Observable<ReviewDto[]>  {
    return this.http.get<ReviewDto[]>(this.URL + 'review?idTechnicianProfile='+id);
  }

  async getAvgReviewByIdTehnician(id:number) : Promise< String | undefined> {
    return await this.http.get<String>(this.URL+'review/'+id+'/grade').toPromise();
  }

  public addReview(review : ReviewDto) : Observable <ReviewDto>{
    return this.http.post<ReviewDto>(this.URL + 'review/create', review,{ responseType: 'text' as 'json' });
  }
}
