package com.example.prova.model.entity;

import java.util.List;

public class CarteiraCliente {
   private Cliente cliente;
   private Vendedor vendedor;
   private Double distancia;
   private CarteiraCliente(Cliente cliente,Vendedor vendedor, Double distancia){
       this.cliente=cliente;
       this.vendedor=vendedor;
       this.distancia=distancia;
   } 
   public static String[] gerarLinha(CarteiraCliente carteiraCliente){
        String[] linha=new String[5];
        linha[0]= carteiraCliente.cliente.getId()+";";
        linha[1]=carteiraCliente.cliente.getRazaoSocial()+";";
        linha[2]=carteiraCliente.vendedor.getId() +";";
        linha[3]=carteiraCliente.vendedor.getNome() +";";
        linha[4]= carteiraCliente.distancia.toString();
        return linha;
   }
   public static CarteiraCliente definirVendedor(List<Vendedor> listaVendedores,Cliente cliente){
       int index=0;
       Double distancia=0.0;
       Double distanciaCalculada=0.0;
       Double[]ponto1 =new Double[2];       
       Double[]ponto2 =new Double[2];
       ponto1=Cliente.pegarPonto(cliente);
       ponto2=Vendedor.pegarPonto(listaVendedores.get(0));
       distancia=distancia(ponto1,ponto2);
       for (int i = 0; i < listaVendedores.size(); i++) {
           ponto2=Vendedor.pegarPonto(listaVendedores.get(i));
           distanciaCalculada=distancia(ponto1,ponto2);
           if (compararDistancias(distancia,distanciaCalculada)) {
               index=i;
               distancia=distanciaCalculada;
           }
       }
       return new CarteiraCliente(cliente, listaVendedores.get(index), distancia);
   }
   public static Double distancia(Double[] ponto1,Double[] ponto2){
    Double distancia=Math.sqrt(Math.pow((ponto1[0]-ponto2[0]),2)+Math.pow((ponto1[1]-ponto2[1]),2));
    return distancia;

   }
   public static boolean compararDistancias(Double distanciaSalva,Double distanciaNova){
        if (distanciaSalva>distanciaNova) {
            return true;
        }  
        return false;
   }

}
