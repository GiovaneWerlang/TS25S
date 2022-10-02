import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Categoria } from 'src/app/models/categoria';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-cadastrar',
  templateUrl: './categoria-cadastrar.component.html',
  styleUrls: ['./categoria-cadastrar.component.css']
})
export class CategoriaCadastrarComponent implements OnInit {

  public categoria = {} as Categoria;

  constructor(private route:ActivatedRoute, private categoriaService:CategoriaService) {
   }

  ngOnInit(): void {
    this.route.params.subscribe( c => {
      let id = c['id'];
      if(id != null){
        this.carregaCategoria(Number(id));
      }
    })
  }

    salvar(){
      if(this.categoria.id == null){
        this.categoriaService.create(this.categoria).subscribe( (res) => {
          console.log(res);
        });
  
      }else{
        this.categoriaService.update(this.categoria).subscribe( (res) => {
          console.log(res);
        });
      }
      this.limpar();
    }
  
    limpar(){
      this.categoria = {} as Categoria;
    }
  
    private carregaCategoria(id:number){
      this.categoriaService.readById(id).subscribe( (categoria) => this.categoria = categoria);
    }

}
