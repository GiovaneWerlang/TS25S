import { Categoria } from "./categoria";

export interface Produto {
    id:number,
    descricao:string,
    marca:string,
    valor:number,
    categoria:Categoria
}