import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vendedor } from './vendedores/vendedor'


@Injectable({
  providedIn: 'root'
})
export class VendedoresService {

  constructor(private http: HttpClient) { }
  salvar(vendedor:Vendedor): Observable<Vendedor>{
    return this.http.post<Vendedor>('http://localhost:8080/api/vendedores',vendedor);
  }
  atualizar(vendedor:Vendedor):Observable<any>{
    return this.http.put<Vendedor>(`http://localhost:8080/api/vendedores/${vendedor.id}`,vendedor);
  }
  getVendedores():Observable<Vendedor[]>{
    return this.http.get<Vendedor[]>('http://localhost:8080/api/vendedores');
  }  
  getVendedoresById(id:number):Observable<Vendedor>{
    return this.http.get<Vendedor>(`http://localhost:8080/api/vendedores/${id}`)    
  }
  deletar(vendedor:Vendedor):Observable<any>{
    return this.http.delete<any>(`http://localhost:8080/api/vendedores/${vendedor.id}`);
  }
}
