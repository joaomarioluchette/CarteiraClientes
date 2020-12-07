package com.example.prova.rest;

import java.util.ArrayList;
import java.util.List;

import com.example.prova.model.entity.Cliente;
import com.example.prova.model.repository.ClienteRepository;

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
@RequestMapping("/api/clientes")
@CrossOrigin("http://localhost:4200")
public class ClienteController {
    private ClienteRepository repository;
    @Autowired
    public ClienteController(ClienteRepository repository){
        this.repository=repository;
    }
    /*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente incluir(@RequestBody @Valid Cliente cliente){
            return repository.save(cliente);       
    }*//*
    @PostMapping
    public ResponseEntity incluir(@RequestBody @Valid Cliente cliente){
        List<String> erros=new ArrayList<>();
        if (Cliente.Validar(cliente,erros)) {
            return new ResponseEntity<Cliente>(repository.save(cliente),HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body(erros);
    }*/
    
    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody  Cliente cliente){
        List<String> erros=new ArrayList<String>();
        erros=Cliente.validarDados(cliente);
        if (repository.findByCnpj(cliente.getCnpj()) != null ){
            erros.add("CNPJ JA CADASTRADO");
        }else {
            if (erros.size()==0) {
                return new ResponseEntity<Object>(repository.save(cliente),HttpStatus.CREATED);
            }
        }
        return ResponseEntity.badRequest().body(erros);
    }    
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualziado){                
        List<String> erros=new ArrayList<String>();  
        Cliente cliente=repository.findById(id).get();
        if (cliente!=null) {
            clienteAtualziado.setId(cliente.getId());
            if (cliente.getCnpj()==clienteAtualziado.getCnpj() ||repository.findByCnpj(clienteAtualziado.getCnpj()) == null ){                 
                return  new ResponseEntity<Object>(repository.save(clienteAtualziado),HttpStatus.NO_CONTENT);               
            }
            erros.add("CPF JA CADASTRADO");
        }else{
            erros.add("VENDEDOR NAO CADASTRADO");  
        }       
        return ResponseEntity.badRequest().body(erros);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        repository.deleteById(id);
    }

    
    @GetMapping("{id}")
    public Cliente pesquisarID(@PathVariable("id") Long id){
        return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public List<Cliente> getAll(){
        return repository.findAll();
    }
}
