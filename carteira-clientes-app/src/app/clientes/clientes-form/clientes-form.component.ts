import { Component, OnInit } from '@angular/core';
import {Cliente} from '../cliente'
import { ClientesService } from '../../clientes.service'
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-clientes-form',
  templateUrl: './clientes-form.component.html',
  styleUrls: ['./clientes-form.component.css']
})
export class ClientesFormComponent implements OnInit {
  cliente: Cliente;
  success:boolean;
  errors:string[];
  id:number;
  constructor( private service: ClientesService,
     private router: Router,
     private activateRoute: ActivatedRoute
     ) {
    this.cliente=new Cliente();
    this.success=false;
   }

  ngOnInit(): void {
    this.id=this.activateRoute.snapshot.params['id'];
    if (this.id) {
      this.service.getClienteById(this.id)
      .subscribe(response=>this.cliente=response,
        errorresponse=>this.cliente=new Cliente());
    }  
  }
  salvarCliente(){
    if (this.id) {
      this.service.atualizar(this.cliente)
      .subscribe(response=>{
        this.success=true;
        this.errors=null;
      },errorResponse=>{
        this.errors=errorResponse.error;
        this.success=false;
      });
    }else{
      this.service.salvar(this.cliente)
      .subscribe(response =>{
        this.success=true;
        this.errors=null;      
        this.cliente=response;
      }, errorResponse=>{
        this.errors=errorResponse.error;
        this.success=false;
      });

    }
  }
  voltarLista(){
    this.router.navigate(['/clientes-lista']);
  }
}
