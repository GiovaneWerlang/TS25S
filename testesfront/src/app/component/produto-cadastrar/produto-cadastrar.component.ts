import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Categoria } from 'src/app/models/categoria';
import { Produto } from 'src/app/models/produto';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produto-cadastrar',
  templateUrl: './produto-cadastrar.component.html',
  styleUrls: ['./produto-cadastrar.component.css']
})
export class ProdutoCadastrarComponent implements OnInit {

  public produto = {} as Produto;
  public categoria:Categoria[] = [];
  public temp:Categoria = {} as Categoria;

  constructor(private route:ActivatedRoute, private produtoService:ProdutoService, private categoriaService:CategoriaService) {
    this.carregarLista(); 
  }

  ngOnInit(): void {
    this.route.params.subscribe( p => {
      let id = p['id'];
      if(id != null){
        this.carregaProduto(Number(id));
      }
    })
  }

    salvar(){
      if(this.produto.id == null){

        this.temp.id = this.produto.categoria.id;
        this.temp.descricao = this.produto.categoria.descricao;
        this.temp.data_inclusao = this.produto.categoria.data_inclusao;
        this.produto.categoria = this.temp;
        this.produtoService.create(this.produto).subscribe( (res) => {
          console.log(res);
        });
  
      }else{

        this.temp.id = this.produto.categoria.id;
        this.temp.descricao = this.produto.categoria.descricao;
        this.temp.data_inclusao = this.produto.categoria.data_inclusao;
        this.produto.categoria = this.temp;

        this.produtoService.update(this.produto).subscribe( (res) => {
          console.log(res);
        });
        
      }
      this.limpar();
    }
  
    limpar(){
      this.produto = {} as Produto;
    }
  
    private carregaProduto(id:number){
      this.produtoService.readById(id).subscribe( (produto) => {
        this.produto = produto;
      });
    }

    carregarLista():void{
      this.categoriaService.readAll().subscribe((categoria)=>{
        this.categoria = categoria;
      })
      
    }

}
