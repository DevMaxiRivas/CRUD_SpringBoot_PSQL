package dev.mycrud.controllers;

import dev.mycrud.model.Book;
import dev.mycrud.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("/book")
    public long createBook(@RequestBody Book newBook){
        return bookService.createBook(newBook);
    }

}
