import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CompariDto } from '../model/compari-dto';

@Injectable({
  providedIn: 'root'
})
export class CompariService {
  private URL = 'http://localhost:8080/api/';
  constructor(private http: HttpClient) { }

  async testCompariEmag(): Promise<CompariDto | undefined>{
    return await this.http.get<CompariDto>(this.URL + "compari/emag").toPromise();
  }

  async testCompariCel(): Promise<CompariDto | undefined>{
    return await this.http.get<CompariDto>(this.URL + "compari/cel").toPromise();
  }

  async testCompariEvomag(): Promise<CompariDto | undefined>{
    return await this.http.get<CompariDto>(this.URL + "compari/evomag").toPromise();
  }
}
