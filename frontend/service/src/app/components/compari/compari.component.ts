import { Component } from '@angular/core';
import { CompariService } from '../../service/compari.service';
import { Router } from '@angular/router';
import { CompariDto } from '../../model/compari-dto';
import { WishListDto } from '../../model/wish-list-dto';
import { WishListService } from '../../service/wish-list.service';

@Component({
  selector: 'app-compari',
  templateUrl: './compari.component.html',
  styleUrl: './compari.component.css',
})
export class CompariComponent {
  emagProducts?: CompariDto[] = [];
  celProducts?: CompariDto[] = [];
  evomagProducts?: CompariDto[] = [];
  searchWord: string = '';
  infRange: string = '';
  supRange: string = '';
  domain: string = '';
  selectedDomain: string = '';
  domainSelected: boolean = false;
  domains: string[] = ['RAM', 'ROM', 'Monitor', 'Headphone', 'Source', 'Cooler', 'Keyboard', 'Mouse', 'Motherboard', 'Video Card', 'Processor'];
  constructor(private router: Router,
              private compariService: CompariService,
              private wishListService: WishListService) {}

  async searchProducts() {
    if (!this.selectedDomain) {
      this.domainSelected = false;
      return;
    }
    const searchTerms = this.searchWord.split(' ');

    const emagPromises = searchTerms.map(term => this.compariService.CompariEmag(term, this.infRange, this.supRange, this.selectedDomain));
    const evomagPromises = searchTerms.map(term => this.compariService.CompariEvomag(term, this.infRange, this.supRange, this.selectedDomain));
    const celPromises = searchTerms.map(term => this.compariService.CompariCel(term, this.infRange, this.supRange, this.selectedDomain));

    const emagResults = await Promise.all(emagPromises);
    const evomagResults = await Promise.all(evomagPromises);
    const celResults = await Promise.all(celPromises);

    this.emagProducts = emagResults.flat().filter(result => result !== undefined) as CompariDto[];
    this.evomagProducts = evomagResults.flat().filter(result => result !== undefined) as CompariDto[];
    this.celProducts = celResults.flat().filter(result => result !== undefined) as CompariDto[];
  }

  addProductToWishList(compariDto: CompariDto):void{
    this.wishListService.addProductToWishList(compariDto).subscribe(
      (response) => {
        console.log('Product added successfully:', response);
      },
      (error) => {
        if(error.status === 200){
          console.log('Product added successfully:');
        }else{
          console.error('Error creating appointment:', error);
        }
      });
  }

}
