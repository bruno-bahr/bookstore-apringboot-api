package dio.bookstoreapi.repository;

import dio.bookstoreapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

        @Query(value = "select b from Book b where b.title like %?1%" )
        List<Book> findByName(String name);
}
