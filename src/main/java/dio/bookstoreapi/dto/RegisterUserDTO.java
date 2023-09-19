package dio.bookstoreapi.dto;

import dio.bookstoreapi.model.UserRole;

public record RegisterUserDTO(String login, String password, UserRole role) {
}
