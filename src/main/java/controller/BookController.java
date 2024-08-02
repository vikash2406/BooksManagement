package controller;


import exception.ResourceNotFoundException;
import model.Books;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.BookService;
import util.Constants;

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

    @PostMapping(path = "")
    public Books createBookDetail(@RequestBody Books data) {
        return bookService.saveBookDetail(new Books(data.getTitle(), data.getAuthor(), data.getPublishYear(), data.getIsbn()));
    }

    @PutMapping(path = "/{id}")
    public Books updateBookDetail(@PathVariable(value = "id") String bookId, @RequestBody Books data) {
        if(Objects.isNull(bookId) || bookId.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        return bookService.saveBookDetail(new Books(data.getTitle(), data.getAuthor(), data.getPublishYear(), data.getIsbn()));
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
    public List<Books> searchBookDetail(@RequestParam String query) {
        if(Objects.isNull(query) || query.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_QUERY_MSG);
        }
        return bookService.searchBookDetail(query);
    }

}
