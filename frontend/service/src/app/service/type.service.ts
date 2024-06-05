import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TypeDto } from '../model/type-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TypeService {
  private URL = 'http://localhost:8080/api/';
  constructor(private http: HttpClient) { }

  async getTypesByIdTechnician(id: number): Promise<TypeDto | undefined> {
    return await this.http.get<TypeDto>(this.URL + 'utils/type'+ id).toPromise();
  }

  public addType(type : TypeDto) : Observable <TypeDto>{
    return this.http.post<TypeDto>(this.URL + 'utils/create', type,{ responseType: 'text' as 'json' });
  }
}
