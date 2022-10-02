import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriaCadastrarComponent } from './component/categoria-cadastrar/categoria-cadastrar.component';
import { CategoriaListarComponent } from './component/categoria-listar/categoria-listar.component';
import { ProdutoCadastrarComponent } from './component/produto-cadastrar/produto-cadastrar.component';
import { ProdutoListarComponent } from './component/produto-listar/produto-listar.component';

const routes: Routes = [
  { path:'cadastrarCategoria', component: CategoriaCadastrarComponent},
  { path:'listarCategoria', component: CategoriaListarComponent},
  { path:'cadastrarProduto', component: ProdutoCadastrarComponent},
  { path:'listarProduto', component: ProdutoListarComponent},
  { path:'editarCategoria/:id', component: CategoriaCadastrarComponent},
  { path:'editarProduto/:id', component: ProdutoCadastrarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
