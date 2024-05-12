import { Component } from '@angular/core';
import { CompariService } from '../../service/compari.service';
import { Router } from '@angular/router';
import { CompariDto } from '../../model/compari-dto';

@Component({
  selector: 'app-compari',
  templateUrl: './compari.component.html',
  styleUrl: './compari.component.css',
})
export class CompariComponent {
  emagProducts?: CompariDto[] = [];
  celProducts?: CompariDto[] = [];
  evomagProducts?: CompariDto[] = [];
  constructor(private router: Router, private compariService: CompariService) {}

  async ngOnInit() {
    const emag =await this.compariService.testCompariEmag();
    if (Array.isArray(emag)) {
      this.emagProducts = emag;
    }
    const evomag =await this.compariService.testCompariEvomag();
    if (Array.isArray(evomag)) {
      this.evomagProducts = evomag;
    }
    const cel =await this.compariService.testCompariCel();
    if (Array.isArray(cel)) {
      this.celProducts = cel;
    }
  }
}
