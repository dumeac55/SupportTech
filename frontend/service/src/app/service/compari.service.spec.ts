import { TestBed } from '@angular/core/testing';

import { CompariService } from './compari.service';

describe('CompariService', () => {
  let service: CompariService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompariService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
