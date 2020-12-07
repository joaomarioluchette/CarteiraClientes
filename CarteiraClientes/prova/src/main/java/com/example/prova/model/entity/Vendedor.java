package com.example.prova.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.validador.Validador;

import lombok.Data;

@Entity
@Data
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 11)
    private String cpf;
    @Column(nullable = false,length=100)
    private String nome;
    @Column(nullable = false, length = 20)
    private String latitude;
    @Column(nullable = false,length = 20)
    private String longitude;

    public static List<String> validarDados(Vendedor vendedor){
        List<String> listaErros=new ArrayList<String>();
        if (!vendedor.cpf.matches("\\d{11}")) {
           listaErros.add("CPF INVALIDO");
        }else{
            if (!Validador.validarCPF(vendedor.cpf)) {
                listaErros.add("CPF INVALIDO");                
            }
        }
        if (!vendedor.nome.matches("(\\w+\\s?)*")) {
            listaErros.add("NOME INVALIDO");
        }
        if (!vendedor.latitude.matches("(\\d+([.]?\\d)*)")) {
           listaErros.add("LATITUDE INVALIDA");
        }
        if (!vendedor.longitude.matches("(\\d+([.]?\\d)*)")) {
            listaErros.add("LONGITUDE INVALIDA");
        }

        return listaErros;
    }
    public static boolean validar(Vendedor vendedor) {
        
        return validarDados(vendedor).size() >0?false:true;
    }
    public static String[] gerarLinha(Vendedor vendedor){
        String[] linha=new String[5];
        linha[0]=vendedor.id.toString()+";";
        linha[2]=vendedor.nome+";";
        linha[1]=vendedor.cpf+";";
        linha[3]=vendedor.latitude+";";
        linha[4]=vendedor.longitude;
        return linha;
   }
   public static Vendedor instanciarLinha(String[] itens){        
       if (itens.length !=4) {
           return null;
       }
       Vendedor vendedor = new Vendedor();
       vendedor.nome=itens[0];
       vendedor.cpf=itens[1];
       vendedor.latitude=itens[2];
       vendedor.longitude=itens[3];
       if (Vendedor.validar(vendedor)) {
           return vendedor;
       }
       return null;
   } 

   public static Double[] pegarPonto(Vendedor vendedor){
       Double[] ponto = new Double[2];
       ponto[0]=Double.valueOf(vendedor.latitude);
       ponto[1]=Double.valueOf(vendedor.longitude);
       return ponto;
   }
}
