package com.example.prova.rest;

import java.util.ArrayList;
import java.util.List;

import com.example.prova.model.entity.Vendedor;
import com.example.prova.model.repository.VendedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/vendedores")
@CrossOrigin("http://localhost:4200")
public class VendedorController {
    
    private VendedorRepository repository;
    @Autowired
    public VendedorController(VendedorRepository repository){
        this.repository=repository;
    }
    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody  Vendedor vendedor){        
        List<String> erros=new ArrayList<String>();       
        erros=Vendedor.validarDados(vendedor);        
        if (repository.findByCpf(vendedor.getCpf()) !=null ){
            erros.add("CPF JA CADASTRADO");
        }else{   
            if (erros.size()==0) {
                return new ResponseEntity<Object>(repository.save(vendedor),HttpStatus.CREATED);
            }
        }
        return ResponseEntity.badRequest().body(erros);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Vendedor vendedorAtualziado){                
        List<String> erros=new ArrayList<String>();  
        Vendedor vendedor=repository.findById(id).get();
        if (vendedor!=null) {
            vendedorAtualziado.setId(vendedor.getId());
            if (vendedor.getCpf()==vendedorAtualziado.getCpf() ||repository.findByCpf(vendedorAtualziado.getCpf()) == null ){                 
                return  new ResponseEntity<Object>(repository.save(vendedorAtualziado),HttpStatus.NO_CONTENT);               
            }
            erros.add("CPF JA CADASTRADO");
        }else{
            erros.add("VENDEDOR NAO CADASTRADO");  
        }       
        return ResponseEntity.badRequest().body(erros);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        repository.deleteById(id);
    }


    @GetMapping("{id}")
    public Vendedor pesquisarID(@PathVariable("id") Long id){
        return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public List<Vendedor> getAll(){
        return repository.findAll();
    }

    
}
