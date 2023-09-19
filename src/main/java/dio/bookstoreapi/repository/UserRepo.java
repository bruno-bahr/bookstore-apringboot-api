package dio.bookstoreapi.repository;

import dio.bookstoreapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<Users, Integer> {
    UserDetails findByLogin(String login);
}
