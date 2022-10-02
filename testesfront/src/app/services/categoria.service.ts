import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Categoria } from '../models/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private enderecoApi = 'http://localhost:8080/categoria';

  constructor(private http:HttpClient) { }

  
  create(categoria:Categoria):Observable<string>{
    return this.http.post(`${this.enderecoApi}`, categoria, {responseType:'text'});
  }
  readAll():Observable<Categoria[]>{
    return this.http.get<Categoria[]>(`${this.enderecoApi}`);
  }
  readById(id:number):Observable<Categoria>{
    return this.http.get<Categoria>(`${this.enderecoApi}/${id}`);
  }
  update(pessoa:Categoria):Observable<string>{
    return this.http.put(`${this.enderecoApi}/${pessoa.id}`, pessoa, {responseType:'text'});
  }
  delete(id:number):Observable<string>{
    return this.http.delete(`${this.enderecoApi}/${id}`, {responseType:'text'});
  }

}
