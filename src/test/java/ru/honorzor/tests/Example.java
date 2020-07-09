package ru.honorzor.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Example {

    @Test
    public void Test() {
        Response response = get("https://reqres.in/api/users?page=2");

        System.out.println(response.getBody());
        System.out.println();
        System.out.println(response.getBody().asString());
        System.out.println();
        System.out.println(response.getContentType());
        System.out.println();
        System.out.println(response.getHeader("Content-Type"));
        System.out.println();
        System.out.println(response.getStatusCode());
        System.out.println();
        System.out.println(response.getBody().prettyPrint());
    }


    @Test
    public void getUser() {
        when()
                .get("https://reqres.in/api/users/2")
        .then()
                .statusCode(200)
                .assertThat().body("data.id", equalTo(2), "data.first_name", equalTo("Janet"))
                .log().all();
    }

    @Test
    public void getUsers() {
        when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .statusCode(200).log().body();
    }

    @Test
    public void getUsersTwo() {
        baseURI = "https://reqres.in/";
        Response response =
        given()
                .param("page","2").
        when()
                .get("/api/users?page=2")
        .then()
                .statusCode(200)
                .extract()
                .response();

        List<Integer> list = response.jsonPath().getList("data.id");

        for(Integer l : list){
            System.out.println(l);
        }

    }

    @Test
    public void postCreateUser() {
        given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"lead\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201).log().all();
    }


    @Test
    public void putUpdateUser() {
        given()
                .header("Content-Type", "Application/Json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .statusCode(200).log().all();
    }
}
