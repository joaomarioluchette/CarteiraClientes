import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArquivoFormComponent } from './arquivo-form/arquivo-form.component';

const routes: Routes = [  {path:'arquivos-form',component:ArquivoFormComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArquivosRoutingModule { }
