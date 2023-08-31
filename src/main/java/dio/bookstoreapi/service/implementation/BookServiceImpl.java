package dio.bookstoreapi.service.implementation;

import dio.bookstoreapi.model.Book;
import dio.bookstoreapi.repository.BookRepo;
import dio.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public List<Book> listAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book searchById(Integer id) {
        Optional<Book> b = bookRepo.findById(id);
        if(b.isPresent()){
            return b.get();
        }
        throw new RuntimeException();
    }

    @Override
    public void addNewBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepo.saveAndFlush(book);
    }

    @Override
    public void deleteBook(Integer id) {
        Optional<Book> bookRemove = bookRepo.findById(id);
        bookRepo.delete(bookRemove.get());
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepo.findByName(name);
    }


}
