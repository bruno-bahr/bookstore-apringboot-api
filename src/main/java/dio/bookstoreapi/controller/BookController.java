package dio.bookstoreapi.controller;

import dio.bookstoreapi.dto.BookCompleteDTO;
import dio.bookstoreapi.dto.BookDTO;
import dio.bookstoreapi.model.Book;
import dio.bookstoreapi.repository.BookRepo;
import dio.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepo bookRepo;

    /**
     * This method will return all the books from the database.
     */
    @GetMapping()
    public ResponseEntity<List<Book>> listAllBooks(){
        return ResponseEntity.ok(bookService.listAllBooks());
    }

    /**
     * This method will return a book based on its id.
     * @param id - the id of the book.
     * @return - it returns the book found in the database.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> searchById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.searchById(id));
    }

    /**
     * This method will add a new in the database.
     * If the book title and the author name match an existing entry,
     * only the field quantity will be updated.
     * @param book - json containing the information
     * @return - it returns the book included or updated in the database.
     */
    @PostMapping()
    @ResponseBody
    public ResponseEntity<Book> addNewBook(@RequestBody BookDTO book){
        String b1 = book.title();
        List<Book> titles = bookRepo.findByName(b1);
        if (!titles.isEmpty() && titles.get(0).getAuthor().equalsIgnoreCase(book.author())){
            Optional<Book> title = titles.parallelStream().findFirst();
            int qtty = title.get().getQuantity() + book.quantity();
            title.get().setQuantity(qtty);
            bookRepo.saveAndFlush(title.get());
            return ResponseEntity.ok(title.get());
        }
        Book newBook = new Book(book.title(), book.author(), book.price(), book.quantity());
        bookService.addNewBook(newBook);
        return ResponseEntity.ok(newBook);
    }

    /**
     * This method will update the values of a particular book
     * based on the parameters received.
     * @param book - it contains the info about the book to be updated.
     * @return - it returns the book selected and its new values.
     */
    @PutMapping()
    public ResponseEntity<Book> updateBook(@RequestBody BookCompleteDTO book){
        Book book2 = bookRepo.findById(book.id()).get();
        book2.setQuantity(book.quantity());
        book2.setAuthor(book.author());
        book2.setPrice(book.price());
        book2.setTitle(book.title());
        bookService.updateBook(book2);
        return ResponseEntity.ok(book2);
    }

    /**
     * This method will delete a book from the database.
     * @param id - it is the value used to do the search in the db.
     * @return - a message will be returned if operation is successful.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestParam Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }

    /**
     * This method will find a book based on its title.
     * @param name - the title of the book.
     * @return - it returns the book found in the database.
     */
    @GetMapping("/name")
    public ResponseEntity<List<Book>> findByName(@RequestParam String name){
        List<Book> book = (List<Book>) bookService.findByName(name);
        return ResponseEntity.ok(book);
    }
}
