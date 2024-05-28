import { Component } from '@angular/core';
import { TypeDto } from '../model/type-dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TechnicianService } from '../service/technician.service';

@Component({
  selector: 'app-type',
  templateUrl: './type.component.html',
  styleUrl: './type.component.css',
})
export class TypeComponent {
  types: TypeDto[] = [];
  typeForm: FormGroup;
  editingType: TypeDto | null = null;

  constructor(private typeService: TechnicianService, private fb: FormBuilder) {
    this.typeForm = this.fb.group({
      idType: [null],
      nameType: ['', Validators.required],
      price: [0, Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes(): void {
    const username = localStorage.getItem('username');
    const id = this.typeService.getUserIdByUserName(username);
    if (id !== undefined) {
      this.typeService
        .getTypesByIdTechnician(id as unknown as number)
        .then((types) => {
          if (Array.isArray(types)) this.types = types;
        });
    }
  }

  onEdit(type: TypeDto): void {
    this.editingType = type;
    this.typeForm.patchValue(type);
  }

  onSubmit(): void {
    const type: TypeDto = this.typeForm.value;
    if (this.editingType) {
    } else {
      this.typeService.addType(type).then(() => {
        this.loadTypes();
        this.typeForm.reset();
      });
    }
  }

  onCancel(): void {
    this.editingType = null;
    this.typeForm.reset();
  }
}
