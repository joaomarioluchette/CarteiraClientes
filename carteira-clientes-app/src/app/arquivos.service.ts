import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArquivosService {
  url: string;
  constructor(private http: HttpClient) {
    this.url='http://localhost:8080/api/arquivos/'
   }

  upload(conteudoArquivo:string,formData: FormData):Observable<any>{
    return this.http.post(`${this.url}${conteudoArquivo}/csv`,formData);
  }
  download(conteudoArquivo:string):Observable<any>{    
    return this.http.get(`${this.url}${conteudoArquivo}/csv`
    , {responseType: "blob", headers: {'Accept': 'text/csv'}});
  }
}
