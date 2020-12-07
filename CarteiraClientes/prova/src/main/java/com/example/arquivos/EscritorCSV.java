package com.example.arquivos;

import java.io.File;
import java.io.FileWriter;

public class EscritorCSV {
    private File arquivo;
    private FileWriter escritor;

    private EscritorCSV(String nome) throws Exception{
        this.arquivo=new File(nome);
        this.escritor= new FileWriter(this.arquivo);  
    }
    public static EscritorCSV iniciarEscritor(String nome){
        EscritorCSV novoEscritor=null;
        try {
             novoEscritor=new EscritorCSV(nome);
        } catch(Exception e) {}
        return novoEscritor;
    }
    public boolean escreverLinha(String[] strs){
        try {
            for (String str : strs) {
                this.escritor.write(str);                   
            }
            this.escritor.write("\n");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean finalizarEscritor(){
        try {
            this.escritor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public File getArquivo(){
        return this.arquivo;
    }
}
