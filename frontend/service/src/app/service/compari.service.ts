import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CompariDto } from '../model/compari-dto';

@Injectable({
  providedIn: 'root'
})
export class CompariService {
  private URL = 'http://localhost:8080/api/';
  constructor(private http: HttpClient) { }

  async CompariEmag(wordSearch:string, infRange:string, supRange:string, domain:string): Promise<CompariDto[] | undefined>{
    return await this.http.get<CompariDto[]>(this.URL + "compari/emag?wordSearch=" + wordSearch + "&infRange=" + infRange + "&supRange=" + supRange + "&domain="+ domain).toPromise();
  }

  async CompariCel(wordSearch:string, infRange:string, supRange:string, domain:string): Promise<CompariDto[] | undefined>{
    return await this.http.get<CompariDto[]>(this.URL + "compari/cel?wordSearch=" + wordSearch + "&infRange=" + infRange +"&supRange=" + supRange+ "&domain="+ domain).toPromise();
  }

  async CompariEvomag(wordSearch:string, infRange:string, supRange:string, domain:string): Promise<CompariDto[] | undefined>{
    return await this.http.get<CompariDto[]>(this.URL + "compari/evomag?wordSearch=" + wordSearch + "&infRange=" + infRange +"&supRange=" + supRange+"&domain="+ domain).toPromise();
  }

  async Domain(wordSearch:string): Promise<CompariDto[] | undefined>{
    return await this.http.get<CompariDto[]>(this.URL + "compari/test?wordSearch=" + wordSearch).toPromise();
  }
}
