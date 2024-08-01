package controller;


import model.Books;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/{id}")
    public Books getBookDetail(@PathVariable(value = "id") String bookId) {
        return bookService.getBookDetail(bookId);
    }

    @GetMapping(path = "")
    public List<Books> getAllBookDetail(@RequestParam(defaultValue = "0")  int page, @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooksDetail(page, size);
    }

    @PostMapping(path = "")
    public Books createBookDetail(@RequestBody Books data) {
        return bookService.saveBookDetail(new Books(data.getTitle(), data.getAuthor(), data.getPublishYear(), data.getIsbn()));
    }

    @PutMapping(path = "/{id}")
    public Books updateBookDetail(@PathVariable(value = "id") String bookId, @RequestBody Books data) {
        return bookService.saveBookDetail(new Books(data.getTitle(), data.getAuthor(), data.getPublishYear(), data.getIsbn()));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBook(@PathVariable(value = "id") String bookId) {
        bookService.deleteBooksDetail(bookId);
    }

    @GetMapping(path = "/search")
    public List<Books> searchBookDetail(@RequestParam String query) {
        return bookService.searchBookDetail(query);
    }

}
