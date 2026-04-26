package exam;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {
    static String id = "";
    static String isbn = "";

    @Test
    @DisplayName("Книги с фильтрацией")
    void getBooksWithParams(){
        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("page", 0);
        queryParams.put("size", 5);
        Methods.getParams(queryParams);
    }

    @Test
    @DisplayName("Книга по id")
    void getBookById() {
        id = "31";
        Methods.getByID(id);
    }

    @Test
    @DisplayName("Книга по isbn")
    void getBookByIsbn(){
        isbn = "978-0141182803";
        Methods.getByIsbn(isbn);
    }

    @Test
    @DisplayName("Создать книгу")
    void addBook(){
        String newBook = new JSONObject()
                .put("isbn", "897-1234567890")
                .put("title", "Магическая битва том.1")
                .put("author", "Гэгэ Агутами")
                .put("genre", "сёнэн")
                .put("year", 2018)
                .put("price", 1000)
                .put("stock", 100)
                .put("pages", 384).toString();
        Methods.postBook(newBook);
    }

    @Test
    @DisplayName("Полное обновление книги")
    void updateBook(){
        Map<String, Integer> pathParam = new HashMap<>();
        pathParam.put("id", 5);

        // JSON для обновления
        String updates = """
        {
            "isbn": "978-0141182803",
            "title": "Магическая битва том.2",
            "author": "Гэгэ Акутами",
            "genre": "сёнэн, боевик",
            "year": 2025,
            "price": 1500,
            "stock": 80,
            "rating": 4.8,
            "pages": 392
        }
        """;
        Methods.putBook(pathParam, updates);
    }

    @Test
    @DisplayName("Частичное обновление книги")
    void patchBook() {
        Map<String, Integer> pathParam = new HashMap<>();
        pathParam.put("id", 5);

        String partialUpdate = new JSONObject()
                .put("price", 1200)
                .put("stock", 50)
                .toString();

        Methods.patchBook(pathParam, partialUpdate);
    }

    @Test
    @DisplayName("Удаление книги по ID")
    void deleteBookById() {
        String id = "5";

        Methods.deleteBook(id);
    }

    @Test
    @DisplayName("Проверка наличия на складе по ID")
    void bookInStock(){
        String id = "5";

        if (Methods.isBookInStock(id)) {
            System.out.println("В наличии!");
        }
    }

    @Test
    @DisplayName("Добавить отзыв на книгу")
    void addBookReview() {
        Map<String, Integer> pathParam = new HashMap<>();
        pathParam.put("id", 5);  // ID книги

        // Минимальный отзыв (только rating)
        String review = new JSONObject()
                .put("rating", 5)
                .put("comment", "Отличная книга!")
                .put("reviewerName", "Сатору Годжо")
                .toString();

        Methods.addBookReview(pathParam, review);
    }

    @Test
    @DisplayName("Получить отзывы на книгу")
    void getBookReviews() {
        Map<String, Integer> pathParam = new HashMap<>();
        pathParam.put("id", 31);

        Methods.getBookReviews(pathParam);
    }
}