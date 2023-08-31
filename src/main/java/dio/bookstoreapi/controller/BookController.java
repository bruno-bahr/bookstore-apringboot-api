package dio.bookstoreapi.controller;

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
    public ResponseEntity<Book> addNewBook(@RequestBody Book book){
        String b1 = book.getTitle();
        List<Book> titles = bookRepo.findByName(b1);
        if (!titles.isEmpty()){
            Optional<Book> title = titles.parallelStream().findFirst();
            int qtty = title.get().getQuantity() + book.getQuantity();
            title.get().setQuantity(qtty);
            bookRepo.saveAndFlush(title.get());
            return ResponseEntity.ok(title.get());
        }
        bookService.addNewBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping()
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        bookService.updateBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@RequestParam Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    public ResponseEntity<List<Book>> findByName(@RequestParam String name){
        List<Book> book = (List<Book>) bookService.findByName(name);
        return ResponseEntity.ok(book);
    }
}
