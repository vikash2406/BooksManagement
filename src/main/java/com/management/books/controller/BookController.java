package com.management.books.controller;


import com.management.books.exception.ResourceNotFoundException;
import com.management.books.model.Books;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.management.books.service.BookService;
import com.management.books.util.Constants;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")

public class BookController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/{id}")
    @Cacheable(key = "#id")
    public Books getBookDetail(@PathVariable(value = "id") String bookId) {
        if(Objects.isNull(bookId) || bookId.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        return bookService.getBookDetail(bookId);
    }

    @GetMapping(path = "")
    public List<Books> getAllBookDetail(@RequestParam(defaultValue = "0")  int page, @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooksDetail(page, size);
    }

    @PostMapping(path = "", consumes ="application/json")
    public Books createBookDetail(@RequestBody Books data) {
        return bookService.saveBookDetail(new Books(data.getTitle(), data.getAuthor(), data.getPublishYear(), data.getIsbn()));
    }

    @PutMapping(path = "/{id}")
    public Books updateBookDetail(@PathVariable(value = "id") String bookId, @RequestBody Books data) {
        if(Objects.isNull(bookId) || bookId.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        Books book = bookService.getBookDetail(bookId);
        if(Objects.isNull(book)){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        book.setTitle(data.getTitle());
        book.setAuthor(data.getAuthor());
        book.setPublishYear(data.getPublishYear());
        book.setIsbn(data.getIsbn());
        return bookService.saveBookDetail(book);
    }

    @DeleteMapping(path = "/{id}")
    @CacheEvict(key = "#id")
    public void deleteBook(@PathVariable(value = "id") String bookId) {
        if(Objects.isNull(bookId) || bookId.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        bookService.deleteBooksDetail(bookId);
    }

    @GetMapping(path = "/search")
    public List<Books> searchBookDetail(@RequestParam(value = "author", required = false) String author,
                                        @RequestParam(value = "title", required = false) String title) {
        return bookService.searchBookDetail(author, title);
    }

}
