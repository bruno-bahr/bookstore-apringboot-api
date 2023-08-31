package dio.bookstoreapi.service;
import dio.bookstoreapi.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> listAllBooks();

    Book searchById(Integer id);

    void addNewBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer id);

    Iterable<Book> findByName(String name);
}
