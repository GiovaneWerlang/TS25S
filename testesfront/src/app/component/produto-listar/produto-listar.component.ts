import { Component, OnInit } from '@angular/core';
import { Produto } from 'src/app/models/produto';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produto-listar',
  templateUrl: './produto-listar.component.html',
  styleUrls: ['./produto-listar.component.css']
})
export class ProdutoListarComponent implements OnInit {
  public produtos: Produto[] = [];

  constructor(private produtoService:ProdutoService) { }

  ngOnInit(): void {
    this.carregarLista();
  }

  delete(id:number){
    this.produtoService.delete(id).subscribe((res)=>{
      console.log(res);
      this.carregarLista();
    })
  }


  carregarLista():void{
    this.produtoService.readAll().subscribe((produto)=>{
      this.produtos = produto;
      this.produtos.forEach( (prod) => {
        console.log(prod.categoria);
      })
      
    })
    

  }

}
