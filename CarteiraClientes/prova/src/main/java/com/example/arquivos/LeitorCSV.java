package com.example.arquivos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class LeitorCSV {
    private BufferedReader leitor;
    private byte[] bytes;
    private InputStream input;

    private LeitorCSV(InputStream input,byte[] bytes) throws Exception{       
        this.input = input;
        this.bytes= bytes;
        InputStreamReader leitorInputStreamReader= new InputStreamReader(input);
        this.leitor=new BufferedReader(leitorInputStreamReader);
    }
    public static LeitorCSV iniciarLeitor(MultipartFile arquivo){        
        try {
            return new LeitorCSV(arquivo.getInputStream(), new byte[(int) arquivo.getSize()]);
        } catch (Exception e) {
            return null;
        }
    }
    public boolean finalizarLeitor(){
        try {
            this.leitor.close();
            this.input.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<String[]> pegarLinha(String separador){
        List<String[]> dados=new ArrayList<String[]>();
        try {
            while (leitor.ready()) {
                dados.add(leitor.readLine().split(separador));                
            }
            
        } catch (Exception e) {
        }
        return dados;
    }
    public byte[] getBytes(){
        return this.bytes;
    }
}
