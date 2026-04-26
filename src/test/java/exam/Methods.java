package exam;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Methods {

    static String HEADER1 = "X-API-Key";
    static String HEADER2 = "bookstore-2026-secret";

    static void getParams(Map queryParams){
        String body = given()
                .params(queryParams)
                .when()
                .log().all()
                .get("http://10.82.196.214:8085/books")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(body);
    }

    static void getByID(String id) {
        System.out.println(id);
        String body = given()
                .when()
                .log().all()
                .get("http://10.82.196.214:8085/books/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(body);
    }

    static void getByIsbn(String isbn) {
        String body = given()
                .when()
                .log().all()
                .get("http://10.82.196.214:8085/books/isbn/"+isbn)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(body);
    }

    static void postBook(String book){
        String body = given()
                .header(HEADER1, HEADER2)
                .when()
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .post("http://10.82.196.214:8085/books")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body()
                .asString();
        System.out.println(body);
    }

    static void putBook(Map pathParams, String updates){
        String body = given()
                .pathParams(pathParams)
                .header("X-API-Key", "bookstore-2026-secret")
                .contentType(ContentType.JSON)
                .body(updates)
                .log().all()
                .when()
                .put("http://10.82.196.214:8085/books/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().asString();
        System.out.println(body);
    }

    static void patchBook(Map<String, Integer> pathParams, String partialUpdates) {
        String body = given()
                .pathParams(pathParams)
                .header(HEADER1, HEADER2)
                .contentType(ContentType.JSON)
                .body(partialUpdates)
                .log().all()
                .when()
                .patch("http://10.82.196.214:8085/books/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(body);
    }

    static void deleteBook(String id) {
        String body = given()
                .header(HEADER1, HEADER2)
                .log().all()
                .when()
                .delete("http://10.82.196.214:8085/books/" + id)
                .then()
                .log().all()
                .statusCode(204)
                .extract().asString();
        System.out.println("Книга ID=" + id + " удалена");
    }

    static boolean isBookInStock(String bookId) {
        Response response = given()
                .header(HEADER1, HEADER2)
                .when()
                .get("http://10.82.196.214:8085/books/" + bookId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        int stock = json.getInt("stock");

        System.out.println("Stock для ID " + bookId + ": " + stock);
        return stock > 0;
    }

    // Добавьте в класс Methods:
    static void addBookReview(Map<String, Integer> pathParams, String reviewJson) {
        String response = given()
                .pathParams(pathParams)
                .header(HEADER1, HEADER2)
                .contentType(ContentType.JSON)
                .body(reviewJson)
                .log().all()
                .when()
                .post("http://10.82.196.214:8085/books/{id}/reviews")
                .then()
                .log().all()
                .statusCode(201)  // Created
                .extract()
                .body()
                .asString();
        System.out.println(response);
    }

    // Добавьте в класс Methods:
    static void getBookReviews(Map<String, Integer> pathParams) {
        String response = given()
                .pathParams(pathParams)
                .header(HEADER1, HEADER2)
                .log().all()
                .when()
                .get("http://10.82.196.214:8085/books/{id}/reviews")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println("Отзывы на книгу: " + response);
    }
}
