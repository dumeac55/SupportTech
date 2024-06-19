import { Component } from '@angular/core';
import { CompariService } from '../../service/compari.service';
import { Router } from '@angular/router';
import { CompariDto } from '../../model/compari-dto';
import { WishListDto } from '../../model/wish-list-dto';
import { WishListService } from '../../service/wish-list.service';
import { NotificationService } from '../../service/notification.service';
import { SignInServiceService } from '../../service/sign-in-service.service';
import { MatDialog } from '@angular/material/dialog';
import { JwtStorageService } from '../../service/jwt-storage.service';
import { JwtDialogComponent } from '../jwt-dialog/jwt-dialog.component';
import { Observable } from 'rxjs';

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
  domains: string[] = [
    'RAM',
    'ROM',
    'Monitor',
    'Headphone',
    'Source',
    'Cooler',
    'Keyboard',
    'Mouse',
    'Motherboard',
    'Video Card',
    'Processor',
  ];
  constructor(
    private router: Router,
    private compariService: CompariService,
    private wishListService: WishListService,
    private notification: NotificationService,
    private dialog: MatDialog,
    private signInService: SignInServiceService,
    private jwt: JwtStorageService,
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token && !this.jwt.isTokenExpired(token)) {
    } else {
      const dialogRef = this.dialog.open(JwtDialogComponent);
      dialogRef.afterClosed().subscribe(() => {
        this.redirectToLogin();
        this.signInService.setIsLogged(false);
        localStorage.removeItem('token');
        localStorage.removeItem('isLogged');
      });
    }
  }
  async searchProducts() {
    if (!this.selectedDomain) {
      this.domainSelected = false;
      return;
    }
    const searchTerms = this.searchWord.split(' ');

    const emagPromises = searchTerms.map((term) =>
      this.compariService.CompariEmag(
        term,
        this.infRange,
        this.supRange,
        this.selectedDomain
      )
    );
    const evomagPromises = searchTerms.map((term) =>
      this.compariService.CompariEvomag(
        term,
        this.infRange,
        this.supRange,
        this.selectedDomain
      )
    );
    const celPromises = searchTerms.map((term) =>
      this.compariService.CompariCel(
        term,
        this.infRange,
        this.supRange,
        this.selectedDomain
      )
    );

    const emagResults = await Promise.all(emagPromises);
    const evomagResults = await Promise.all(evomagPromises);
    const celResults = await Promise.all(celPromises);

    this.emagProducts = emagResults
      .flat()
      .filter((result) => result !== undefined) as CompariDto[];
    this.evomagProducts = evomagResults
      .flat()
      .filter((result) => result !== undefined) as CompariDto[];
    this.celProducts = celResults
      .flat()
      .filter((result) => result !== undefined) as CompariDto[];
  }

  addProductToWishList(compariDto: CompariDto): void {
    this.wishListService.addProductToWishList(compariDto).subscribe(
      (response) => {
        this.notification.showNotification('Product added successfully');
      },
      (error) => {
        if (error.status === 200)
          this.notification.showNotification('Product added successfully');
      }
    );
  }

  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
