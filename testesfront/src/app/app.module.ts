import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './component/app-root/app.component';
import { CategoriaCadastrarComponent } from './component/categoria-cadastrar/categoria-cadastrar.component';
import { CategoriaListarComponent } from './component/categoria-listar/categoria-listar.component';
import { ProdutoCadastrarComponent } from './component/produto-cadastrar/produto-cadastrar.component';
import { ProdutoListarComponent } from './component/produto-listar/produto-listar.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ProdutoCadastrarComponent,
    ProdutoListarComponent,
    CategoriaCadastrarComponent,
    CategoriaListarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
