import { Component, OnInit } from '@angular/core';
import { Categoria } from 'src/app/models/categoria';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-listar',
  templateUrl: './categoria-listar.component.html',
  styleUrls: ['./categoria-listar.component.css']
})
export class CategoriaListarComponent implements OnInit {

  public categoria: Categoria[] = [];

  constructor(private categoriaService:CategoriaService) { }

  ngOnInit(): void {
    this.carregarLista();
  }

  delete(id:number){
    this.categoriaService.delete(id).subscribe((res)=>{
      console.log(res);
      this.carregarLista();
    })
  }


  carregarLista():void{
    console.log('teste');
    this.categoriaService.readAll().subscribe((categoria)=>{
      this.categoria = categoria;
    })
  }

}
