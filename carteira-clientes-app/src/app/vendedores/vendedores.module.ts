import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VendedoresRoutingModule } from './vendedores-routing.module';
import { VendedoresFormComponent } from './vendedores-form/vendedores-form.component';
import { VendedoresListComponent } from './vendedores-lista/vendedores-lista.component';
import { FormsModule } from '@angular/forms';
import { OrderModule } from 'ngx-order-pipe';


@NgModule({
  declarations: [VendedoresFormComponent, VendedoresListComponent],
  imports: [
    CommonModule,
    VendedoresRoutingModule,
    FormsModule,
    OrderModule
  ],exports:[VendedoresFormComponent, VendedoresListComponent]
})
export class VendedoresModule { }
