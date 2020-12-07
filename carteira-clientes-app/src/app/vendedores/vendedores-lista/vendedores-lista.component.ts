import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VendedoresService } from 'src/app/vendedores.service';
import { Vendedor } from '../vendedor';


@Component({
  selector: 'app-vendedores-list',
  templateUrl: './vendedores-lista.component.html',
  styleUrls: ['./vendedores-lista.component.css']
})
export class VendedoresListComponent implements OnInit {
  vendedores: Vendedor[]=[];
  vendedorDelecao: Vendedor;
  mensagemSucesso: string;
  mensagemErro:string;
  chave:string;
  sentido:boolean;
  tempFiltroId:string;
  tempFiltroNome:string;
  filtroId:string;
  filtroNome:string;
  constructor(
    private service:VendedoresService,
    private router:Router
    ) { }

  ngOnInit(): void {
    this.service.getVendedores()
    .subscribe(response=>this.vendedores=response);
    this.sentido=false;
    this.chave='nome';
    this.limparFiltro()
  }
  novoCadastro(){
    this.router.navigate(['/vendedores-form']);
  }

  preparaDelecao(vendedor:Vendedor){
    this.vendedorDelecao=vendedor;
  }
  ordenar(chave: string){
    if (this.chave==chave) {
      this.sentido=!this.sentido
    }else{
      this.sentido=false;
    }
    this.chave=chave;
  }
  deletarVendedor(){
    this.service
    .deletar(this.vendedorDelecao)
    .subscribe(response =>{
      this.mensagemSucesso='Vendedor deletado com sucesso';
      this.ngOnInit();
    },erro=> this.mensagemErro='Ocorreu um erro ao deletar o Vendedor')
  };
  filtro(lista:Vendedor[]): Vendedor[]{
    if ((this.filtroId=='' && this.filtroNome=='') || this.vendedores.length==0) {
      return this.vendedores;      
    }
    let result: Vendedor[] = []  
    lista.forEach(item=>{    
      if (this.filtroId !='' &&this.filtroNome =='' ) {
        if (item.id.toString()==(this.filtroId)) {
          result.push(item);    
        }        
      }
      if (this.filtroId =='' &&this.filtroNome !='' ) {
        if (item.nome.startsWith(this.filtroNome)) {
          result.push(item);    
        }        
      }      
      if (this.filtroId !='' &&this.filtroNome !='' ) {
        if (item.nome.startsWith(this.filtroNome) && item.id === Number(this.filtroId)) {
          result.push(item);        
        }        
      }
    })
    return result;
  }
  definirFiltro(){
    this.filtroId=this.tempFiltroId;
    this.filtroNome=this.tempFiltroNome;
  }
  limparFiltro(){
    this.filtroId='';
    this.filtroNome='';
    this.tempFiltroId='';
    this.tempFiltroNome='';
  }
  comparador(v1,v2){
    if (v1.type !== 'string' || v2.type !== 'string') {
      return (v1.index < v2.index) ? -1 : 1;
    }
  }
}
