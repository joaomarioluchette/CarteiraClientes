import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ArquivosRoutingModule } from './arquivos-routing.module';
import { ArquivoFormComponent } from './arquivo-form/arquivo-form.component';


@NgModule({
  declarations: [ArquivoFormComponent],
  imports: [
    CommonModule,
    ArquivosRoutingModule
  ],exports:[ArquivoFormComponent]
})
export class ArquivosModule { }
