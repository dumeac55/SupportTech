import { Injectable } from '@angular/core';
import { WishListDto } from '../model/wish-list-dto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompariDto } from '../model/compari-dto';
@Injectable({
  providedIn: 'root'
})
export class WishListService {
  private URL = 'http://localhost:8080/api/';
  private id?: number;
  constructor(private http:HttpClient) { }

  async getWishlistForUser(): Promise <WishListDto[] | undefined>{
    this.id = await this.getUserIdByUserName(localStorage.getItem('username'));
    return await this.http.get<WishListDto[]>(this.URL + "wishlist/" + this.id).toPromise();
  }
  async getUserIdByUserName(username: string | null): Promise<number | undefined> {
    return await this.http.get<number>(this.URL + "user/username=" + username).toPromise();
  }
  addProductToWishList(compariDto: CompariDto): Observable<any> {
    const wishListDto: WishListDto = {
      nameProduct: "",
      price: "",
      company: "",
      linkImage: "",
      linkProduct: "",
      idWishlist: NaN,
      idUser: this.id,
      idProduct: compariDto.idProduct
    };
    return this.http.post<any>(this.URL + "wishlist/create", wishListDto,{responseType: 'text' as 'json'});
  }

  deleteProductFromWishList(id: number): Observable<any>{
    return this.http.delete<any>(this.URL+ "wishlist/delete/" + id);
  }
}
