package com.example.prova.model.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.example.validador.Validador;

import lombok.Data;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length=14)
    @NotEmpty()
    private String cnpj;
    
    @Column(nullable = false,length=100)    
    @NotEmpty()
    private String razaoSocial;
    
    @Column(nullable = false,length = 20)    
    @NotEmpty()
    private String latitude;
    
    @Column(nullable = false,length = 20)    
    @NotEmpty()
    private String longitude;
    public static List<String> Validar(Cliente cliente, List<String> listaErros){
    
        return listaErros;
    }
    public static boolean validar(Cliente cliente) {
        
        return validarDados(cliente).size() >0?false:true;
    }
    public static String[] gerarLinha(Cliente cliente){
        String[] linha=new String[5];
        linha[0]=cliente.id.toString()+";";
        linha[2]=cliente.razaoSocial+";";
        linha[1]=cliente.cnpj+";";
        linha[3]=cliente.latitude+";";
        linha[4]=cliente.longitude;
        return linha;
   }
   public static Cliente instanciarLinha(String[] itens){
        if (itens.length !=4) {
            return null;
        }
        Cliente cliente=new Cliente();
        cliente.razaoSocial=itens[0];
        cliente.cnpj=itens[1];
        cliente.latitude=itens[2];
        cliente.longitude=itens[3];
        if (validar(cliente)) {
            return cliente;
        }
        return null;       
   }

   public static Double[] pegarPonto(Cliente cliente){
       Double[] ponto = new Double[2];
       ponto[0]=Double.valueOf(cliente.latitude);
       ponto[1]=Double.valueOf(cliente.longitude);
       return ponto;
   }
   public static List<String> validarDados(Cliente cliente){
    List<String> listaErros=new ArrayList<String>();
    if (!cliente.cnpj.matches("\\d{14}")) {
       listaErros.add("CNPJ INVALIDO");
    }else{
        if (!Validador.validarCNPJ(cliente.cnpj)) {
            listaErros.add("CNPJ INVALIDO");                
        }
    }
    if (!cliente.razaoSocial.matches("(\\w+\\s?)*")) {
        listaErros.add("RAZAO SOCIAL INVALIDA");
    }
    if (!cliente.latitude.matches("(\\d+([.]?\\d)*)")) {
       listaErros.add("LATITUDE INVALIDA");
    }
    if (!cliente.longitude.matches("(\\d+([.]?\\d)*)")) {
        listaErros.add("LONGITUDE INVALIDA");
    }

    return listaErros;
}

}
