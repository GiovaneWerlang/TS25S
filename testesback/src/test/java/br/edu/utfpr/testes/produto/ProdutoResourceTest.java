package br.edu.utfpr.testes.produto;

import br.edu.utfpr.testes.categoria.Categoria;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoResourceTest {

    @TestHTTPResource("/produto")
    URL apiURL;

    @TestHTTPResource("/produto/1")
    URL idURL;

    @TestHTTPResource("/produto/321")
    URL erroURL;


    @Test
    @Order(1)
    @DisplayName("Deve criar produto com sucesso.")
    public void createProdutoTest() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");

        PreparedStatement stmt = c.prepareStatement("INSERT INTO categoria (descricao,data_inclusao)" +
                " VALUES ('Salgado','01/10/2022')");
        stmt.execute();
        stmt.close();

        c.close();

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao("Biscoito");
        produtoDTO.setMarca("Neugebauer");
        produtoDTO.setValor(5);
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Salgado");
        categoria.setData_inclusao("01/10/2022");
        produtoDTO.setCategoria(categoria);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(produtoDTO)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals( 201, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Deve falhar ao criar produto.")//
    public void createProdutoValidationErrorTest() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");

        PreparedStatement stmt = c.prepareStatement("INSERT INTO categoria (descricao,data_inclusao)" +
                " VALUES ('Salgado','01/10/2022')");
        stmt.execute();
        stmt.close();

        c.close();


        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao(null);
        produtoDTO.setMarca(null);
        produtoDTO.setValor(0);
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Salgado");
        categoria.setData_inclusao("01/10/2022");
        produtoDTO.setCategoria(categoria);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(produtoDTO)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals( 422, response.getStatusCode());
        assertEquals("Erro de validação de campos.", response.jsonPath().getString("message"));

    }

    @Order(3)
    @Test
    @DisplayName("Deve atualizar produto com sucesso.")
    public void updateProdutoTest() throws SQLException {

        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");

        PreparedStatement stmt = c.prepareStatement("INSERT INTO categoria (descricao,data_inclusao)" +
                " VALUES ('Salgado','01/10/2022')");
        stmt.execute();
        stmt.close();

        c.close();

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao("Biscoito");
        produtoDTO.setMarca("Neugebauer");
        produtoDTO.setValor(5);
        Categoria categoria = new Categoria();
        categoria.setId(3);
        categoria.setDescricao("Salgado");
        categoria.setData_inclusao("01/10/2022");
        produtoDTO.setCategoria(categoria);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(produtoDTO)
                .when()
                .put(idURL)
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode());
    }

    @Order(4)
    @Test
    @DisplayName("Deve falhar ao atualizar produto.")
    public void updateProdutoValidationErrorTest() throws SQLException {

        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");

        PreparedStatement stmt = c.prepareStatement("INSERT INTO categoria (descricao,data_inclusao)" +
                " VALUES ('Salgado','01/10/2022')");
        stmt.execute();
        stmt.close();

        c.close();

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao(null);
        produtoDTO.setMarca(null);
        produtoDTO.setValor(0);
        Categoria categoria = new Categoria();
        categoria.setId(4);
        categoria.setDescricao("Salgado");
        categoria.setData_inclusao("01/10/2022");
        produtoDTO.setCategoria(categoria);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(produtoDTO)
                .when()
                .put(idURL)
                .then()
                .extract().response();

        assertEquals( 422, response.getStatusCode());
        assertEquals("Erro de validação de campos.", response.jsonPath().getString("message"));

    }

    @Order(5)
    @Test
    @DisplayName("Deve buscar produto por id com sucesso.")
    public void getByIdProdutoTest(){


        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(idURL)
                .then()
                .extract().response();

        assertEquals( 200, response.getStatusCode());
    }

    @Order(6)
    @Test
    @DisplayName("Deve falhar ao buscar produto por id.")
    public void getByIdProdutoValidationErrorTest(){


        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(erroURL)
                .then()
                .extract().response();

        assertEquals( 404, response.getStatusCode());
    }

    @Order(7)
    @Test
    @DisplayName("Deve buscar todos os produtos com sucesso.")
    public void getAllProdutoTest(){



        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(apiURL)
                .then()
                .extract().response();

        assertEquals( 200, response.getStatusCode());
    }

    @Order(8)
    @Test
    @DisplayName("Deve deletar por id o produto com sucesso.")
    public void deleteProdutoTest(){


        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(idURL)
                .then()
                .extract().response();

        assertEquals( 200, response.getStatusCode());
    }

    @Order(9)
    @Test
    @DisplayName("Deve falhar ao deletar por id o produto.")
    public void deleteProdutoErrorTest(){


        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(erroURL)
                .then()
                .extract().response();

        assertEquals( 404, response.getStatusCode());
    }

    @Order(10)
    @Test
    @DisplayName("Deve falhar ao buscar todos os produtos.")
    public void getAllProdutoErrorTest() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");
        PreparedStatement stmt = c.prepareStatement("delete from produto");
        stmt.execute();
        stmt.close();
        c.close();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(apiURL)
                .then()
                .extract().response();

        assertEquals( 404, response.getStatusCode());
    }
}
