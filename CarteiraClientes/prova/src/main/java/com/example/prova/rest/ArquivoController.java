package com.example.prova.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.example.arquivos.EscritorCSV;
import com.example.arquivos.LeitorCSV;
import com.example.prova.model.entity.CarteiraCliente;
import com.example.prova.model.entity.Cliente;
import com.example.prova.model.entity.Vendedor;
import com.example.prova.model.repository.ClienteRepository;
import com.example.prova.model.repository.VendedorRepository;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/arquivos")
@CrossOrigin("http://localhost:4200")
public class ArquivoController {
    private ClienteRepository repositoryCliente;
    private VendedorRepository repositoryVendedores;
    @Autowired
    public ArquivoController(ClienteRepository repositoryCliente,VendedorRepository repositoryVendedores){
        this.repositoryCliente=repositoryCliente;
        this.repositoryVendedores=repositoryVendedores;
    }
    @PostMapping("{tipo}/csv")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<int[]> importacaoCSV(@PathVariable("tipo") String tipo, @RequestParam("csv") MultipartFile  arquivo) {
        int[] resultados={0,0};
        LeitorCSV leitorCSV=LeitorCSV.iniciarLeitor(arquivo);
        if (tipo.equals("vendedor")) {
            resultados=importarCSVVendedor(leitorCSV.pegarLinha(";"));
        } else if (tipo.equals("cliente")) {
            resultados=importarCSVCliente(leitorCSV.pegarLinha(";"));
        }
        leitorCSV.finalizarLeitor();
        return ResponseEntity.ok().body(resultados);
    }

    @GetMapping("{tipo}/csv")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Resource> exportacaoCSV(@PathVariable("tipo") String tipo) {
        File file = null;
        if (  tipo.equals("vendedor") ) {
            file = exportarVendedores();
        } else if (tipo.equals("cliente")) {
            file = exportarClientes();
        }else if (tipo.equals("carteira-cliente")) {
            file=exportarCarteiraCliente();
        }
        if (file != null) {
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+tipo+".csv");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
            InputStreamResource resource=null;
            try {
                resource = new InputStreamResource(new FileInputStream(file));                
                 return ResponseEntity.ok()
                 .headers(header)
                 .contentLength(file.length())
                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                 .body(resource);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private File exportarVendedores(){
        EscritorCSV escritorCSV=EscritorCSV.iniciarEscritor("Clientes.csv");     
        List<Vendedor> lista=repositoryVendedores.findAll();
        for (Vendedor vendedor : lista) {
            escritorCSV.escreverLinha(Vendedor.gerarLinha(vendedor));
        }
        escritorCSV.finalizarEscritor();
        return escritorCSV.getArquivo();
    }
    private File exportarClientes(){
        EscritorCSV escritorCSV=EscritorCSV.iniciarEscritor("Clientes.csv");                    
        List<Cliente> lista=repositoryCliente.findAll();
        for (Cliente cliente : lista) {
            escritorCSV.escreverLinha(Cliente.gerarLinha(cliente));
        }
        escritorCSV.finalizarEscritor();
        return escritorCSV.getArquivo();
    }
    private File exportarCarteiraCliente(){        
        EscritorCSV escritorCSV=EscritorCSV.iniciarEscritor("CarteiraCliente.csv");                    
        List<CarteiraCliente> lista=gerarCarteiraCliente();        
        for (CarteiraCliente carteiraCliente : lista) {
            escritorCSV.escreverLinha(CarteiraCliente.gerarLinha(carteiraCliente));
        }
        escritorCSV.finalizarEscritor();
        return escritorCSV.getArquivo();
    }
    private List<CarteiraCliente> gerarCarteiraCliente(){                 
        List<Cliente> listaClientes=repositoryCliente.findAll();           
        List<Vendedor> listaVendedores=repositoryVendedores.findAll();
        List<CarteiraCliente> listaCarteiraCliente= new ArrayList<CarteiraCliente>();

        for (Cliente cliente : listaClientes) {
            listaCarteiraCliente.add(CarteiraCliente.definirVendedor(listaVendedores,cliente));
        }

        return listaCarteiraCliente;
    }

    private int[] importarCSVVendedor(List<String[]> dados){
        Vendedor novo;
        int cadastros=0;
        int erros=0;
        for (String[] linha : dados) {
            novo=Vendedor.instanciarLinha(linha);
            if (novo!=null && repositoryVendedores.findByCpf(novo.getCpf()) ==null) {
                cadastros++;
                repositoryVendedores.save(novo);
            }else{
                erros++;
            }
        }
        return new int[]{cadastros,erros};
    }

    private int[] importarCSVCliente(List<String[]> dados){
        Cliente novo;
        int cadastros=0;
        int erros=0;
        for (String[] linha : dados) {
            novo=Cliente.instanciarLinha(linha);
            if (novo!=null && repositoryCliente.findByCnpj(novo.getCnpj()) ==null) {
                cadastros++;
                repositoryCliente.save(novo);
            }else{
                erros++;
            }
        }
        return new int[]{cadastros,erros};
        
    }
}
