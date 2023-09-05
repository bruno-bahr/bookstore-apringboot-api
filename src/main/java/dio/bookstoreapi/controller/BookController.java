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

    @GetMapping()
    public ResponseEntity<List<Book>> listAllBooks(){
        return ResponseEntity.ok(bookService.listAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> searchById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.searchById(id));
    }

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

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestParam Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }

    @GetMapping("/name")
    public ResponseEntity<List<Book>> findByName(@RequestParam String name){
        List<Book> book = (List<Book>) bookService.findByName(name);
        return ResponseEntity.ok(book);
    }
}
