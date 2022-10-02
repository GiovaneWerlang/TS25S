package br.edu.utfpr.testes.categoria;

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
public class CategoriaResourceTest {

    @TestHTTPResource("/categoria")
    URL apiURL;

    @TestHTTPResource("/categoria/1")
    URL idURL;

    @TestHTTPResource("/categoria/321")
    URL erroURL;

    @Test
    @Order(1)
    @DisplayName("Deve criar categoria com sucesso.")
    public void createCategoriaTest(){
        CategoriaDTO categoriaDto = new CategoriaDTO();
        categoriaDto.setDescricao("Salgado");
        categoriaDto.setData_inclusao("01/10/2022");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(categoriaDto)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals( 201, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Deve falhar ao criar categoria.")
    public void createCategoriaValidationErrorTest(){
        CategoriaDTO categoriaDto = new CategoriaDTO();
        categoriaDto.setDescricao(null);
        categoriaDto.setData_inclusao(null);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(categoriaDto)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals( 422, response.getStatusCode());
        assertEquals("Erro de validação de campos.", response.jsonPath().getString("message"));
    }

    @Order(3)
    @Test
    @DisplayName("Deve atualizar categoria com sucesso.")
    public void updateCategoriaTest(){

        CategoriaDTO categoriaDto = new CategoriaDTO();
        categoriaDto.setDescricao("Salgado");
        categoriaDto.setData_inclusao("01/10/2022");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(categoriaDto)
                .when()
                .put(idURL)
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode());
    }

    @Order(4)
    @Test
    @DisplayName("Deve falhar ao atualizar categoria.")
    public void updateCategoriaValidationErrorTest(){

        CategoriaDTO categoriaDto = new CategoriaDTO();
        categoriaDto.setDescricao(null);
        categoriaDto.setData_inclusao(null);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(categoriaDto)
                .when()
                .put(idURL)
                .then()
                .extract().response();

        assertEquals( 422, response.getStatusCode());
        assertEquals("Erro de validação de campos.", response.jsonPath().getString("message"));

    }

    @Order(5)
    @Test
    @DisplayName("Deve buscar categoria por id com sucesso.")
    public void getByIdCategoriaTest(){


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
    @DisplayName("Deve falhar ao buscar categoria por id.")
    public void getByIdCategoriaValidationErrorTest(){


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
    @DisplayName("Deve buscar todas as categorias com sucesso.")
    public void getAllCategoriaTest(){


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
    @DisplayName("Deve deletar por id a categoria com sucesso.")
    public void deleteCategoriaTest(){


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
    @DisplayName("Deve falhar ao deletar por id a categoria.")
    public void deleteCategoriaErrorTest(){


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
    @DisplayName("Deve falhar ao buscar todas as categorias.")
    public void getAllCategoriaErrorTest() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        Connection c = DriverManager.getConnection("jdbc:h2:mem:db;IFEXISTS=TRUE", "sa", "sa");
        PreparedStatement stmt = c.prepareStatement("delete from categoria");
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