import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VendedoresService } from 'src/app/vendedores.service';
import { Vendedor } from '../vendedor';

@Component({
  selector: 'app-vendedores-form',
  templateUrl: './vendedores-form.component.html',
  styleUrls: ['./vendedores-form.component.css']
})
export class VendedoresFormComponent implements OnInit {
  vendedor:Vendedor;
  success:boolean;
  errors:string[];
  id:number;
  constructor(
    private service: VendedoresService,
    private router:Router,
    private activateRoute: ActivatedRoute
    ) { 
      this.vendedor= new Vendedor;
      this.success=false;
    }

  ngOnInit(): void {
    this.id=this.activateRoute.snapshot.params['id'];
    if (this.id) {
      this.service.getVendedoresById(this.id)
      .subscribe(response=>this.vendedor=response,
        errorResponse=>this.vendedor=new Vendedor());
    }
  }
  salvarVendedor(){
    if (this.vendedor.id) {
      this.service.atualizar(this.vendedor)
      .subscribe(response=>{
        this.success=true;
        this.errors=null;
      },errorResponse=>{
        this.errors=errorResponse.error;
        this.success=false;
      });
    }else{
      this.service.salvar(this.vendedor)
      .subscribe(response =>{
        this.success=true;
        this.errors=null;      
        this.vendedor=response;
      }, errorResponse=>{
        this.errors=errorResponse.error;
        this.success=false;
      });

    }
  }
  
  voltarLista(){
    this.router.navigate(['/vendedores-lista']);
  }

}
