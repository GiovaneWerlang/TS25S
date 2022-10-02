import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Produto } from '../models/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  private enderecoApi = 'http://localhost:8080/produto';

  constructor(private http:HttpClient) { }

  create(produto:Produto):Observable<string>{
    return this.http.post(`${this.enderecoApi}`, produto, {responseType:'text'});
  }
  readAll():Observable<Produto[]>{
    return this.http.get<Produto[]>(`${this.enderecoApi}`);
  }
  readById(id:number):Observable<Produto>{
    return this.http.get<Produto>(`${this.enderecoApi}/${id}`);
  }
  update(produto:Produto):Observable<string>{
    return this.http.put(`${this.enderecoApi}/${produto.id}`, produto, {responseType:'text'});
  }
  delete(id:number):Observable<string>{
    return this.http.delete(`${this.enderecoApi}/${id}`, {responseType:'text'});
  }

}
