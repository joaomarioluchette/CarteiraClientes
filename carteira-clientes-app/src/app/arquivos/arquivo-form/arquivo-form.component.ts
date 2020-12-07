import { Component, OnInit } from '@angular/core';
import { ArquivosService } from 'src/app/arquivos.service';
import { saveAs } from 'file-saver'

@Component({
  selector: 'app-arquivo-form',
  templateUrl: './arquivo-form.component.html',
  styleUrls: ['./arquivo-form.component.css']
})
export class ArquivoFormComponent implements OnInit {
  filesVendedor: File[];
  filesClientes:File[];
  formatoErrado: boolean;
  mensagem:string;
  constructor(private service: ArquivosService) { }

  ngOnInit(): void {
  }
  definirArquivosVendedor(event){
    this.filesVendedor=event.target.files;
  }
  importarVendedores(){
    let valores:number[]=[];
    if (this.filesVendedor) {
      if (!(this.filesVendedor[0].name.split('.')[1]=='csv')) {
        this.mensagem='Formato invalido, utilize arquivos na extensao CSV !!!'
        return;
      }
      this.mensagem='Arquivo Enviado com sucesso. Aguarde !!!';
      const file:File=this.filesVendedor[0];
      const formData:FormData=new FormData();
      formData.append("csv",file);
      this.service.upload('vendedor',formData)
      .subscribe(response=> {
        valores=response;
        this.mensagem=`Foram importados ${valores[0]} Vendedores!! Ocorreu algum problema em ${valores[1]} Vendedores`;
      });
    }else{
      this.mensagem="Escolha um arquivo";
    }
  }
  definirArquivosCliente(event){
    this.filesClientes=event.target.files;
  }
  importarClientes(){
    
    let valores:number[]=[];
    if (this.filesClientes) {
      if (!(this.filesClientes[0].name.split('.')[1]=='csv')) {
        this.mensagem='Formato invalido, utilize arquivos na extensao CSV !!!'
        return;
      }
      this.mensagem='Arquivo Enviado com sucesso. Aguarde !!!';
      const formData:FormData=new FormData();
      formData.append("csv",this.filesClientes[0]);
      this.service.upload('cliente',formData)
      .subscribe(response=> {
        valores=response;
        this.mensagem=`Foram importados ${valores[0]} Clientes!! Ocorreu algum problema em ${valores[1]} Clientes`;
      });
    }else{
      this.mensagem="Escolha um arquivo";
    }
  }
  exportarClientes(){
    this.service.download("cliente")
    .subscribe(response=>{ saveAs(response,'Clientes.csv') });
  }
  exportarVendedores(){
    this.service.download("vendedor")
    .subscribe(response=>{ saveAs(response,'Vendedores.csv') });
  }
  exportarCarteiraClientes(){    
    this.service.download("carteira-cliente")
    .subscribe(response=>{ saveAs(response,'CarteiraCliente.csv') });
  }
}
