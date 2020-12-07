import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VendedoresFormComponent } from './vendedores-form/vendedores-form.component';
import { VendedoresListComponent } from './vendedores-lista/vendedores-lista.component';

const routes: Routes = [
  {path:'vendedores-form',component:VendedoresFormComponent},
  {path:'vendedores-form/:id',component:VendedoresFormComponent},
  {path:'vendedores-lista',component:VendedoresListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VendedoresRoutingModule { }
