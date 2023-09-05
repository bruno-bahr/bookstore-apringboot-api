package dio.bookstoreapi.dto;

public record BookCompleteDTO(Integer id, String title, String author, Double price, int quantity) {
}
