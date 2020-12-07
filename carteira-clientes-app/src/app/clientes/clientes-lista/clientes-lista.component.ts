import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

import { ClientesService } from 'src/app/clientes.service';
import { Cliente } from '../cliente';

@Component({
  selector: 'app-clientes-lista',
  templateUrl: './clientes-lista.component.html',
  styleUrls: ['./clientes-lista.component.css']
})
export class ClientesListaComponent implements OnInit {

  clientes: Cliente[]=[];
  clienteDelecao: Cliente;
  mensagemSucesso: string;
  mensagemErro:string;
  chave:string;
  sentido:boolean;
  filtroRazao:string;
  filtroId:string
  tempFiltroRazao:string;
  tempFiltroId:string;
  constructor(private service: ClientesService, private router: Router) { 
  }

  ngOnInit(): void {
    this.service.getClientes().subscribe(resposta=> this.clientes=resposta);
    this.chave='razaoSocial';
    this.sentido=false
    this.limparFiltro()
  }
  
  novoCadastro(){
    this.router.navigate(['/clientes-form']);
  }
  preparaDelecao(cliente:Cliente){
    this.clienteDelecao=cliente;
  }
  deletarCliente(){
    this.service
    .deletar(this.clienteDelecao)
    .subscribe(response =>{
      this.mensagemSucesso='Cliente deletado com sucesso';
      this.ngOnInit();
    },erro=> this.mensagemErro='Ocorreu um erro ao deletar o Cliente')
  };
  ordenar(chave:string){
    if (this.chave==chave) {
      this.sentido=!this.sentido
    }else{
      this.sentido=false;
    }
    this.chave=chave;

  }
  comparador(v1,v2){
    if (v1.type !== 'string' || v2.type !== 'string') {
      return (v1.index < v2.index) ? -1 : 1;
    }
  }
  filtro(lista:Cliente[]): Cliente[]{
    if ((this.filtroId=='' && this.filtroRazao=='') || this.clientes.length==0) {
      return this.clientes;      
    }
    let result: Cliente[] = []  
    lista.forEach(item=>{    
      if (this.filtroId !='' &&this.filtroRazao =='' ) {
        if (item.id.toString()==(this.filtroId)) {
          result.push(item);    
        }        
      }
      if (this.filtroId =='' &&this.tempFiltroRazao !='' ) {
        if (item.razaoSocial.startsWith(this.filtroRazao)) {
          result.push(item);    
        }        
      }      
      if (this.filtroId !='' &&this.tempFiltroRazao !='' ) {
        if (item.razaoSocial.startsWith(this.filtroRazao) && item.id === Number(this.filtroId)) {
          result.push(item);        
        }        
      }
    })
    return result;
  }
  definirFiltro(){
    this.filtroId=this.tempFiltroId;
    this.filtroRazao=this.tempFiltroRazao;
  }
  limparFiltro(){
    this.filtroId='';
    this.filtroRazao='';
    this.tempFiltroId='';
    this.tempFiltroRazao='';
  }
  
}
