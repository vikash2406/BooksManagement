package com.management.books.service.impl;


import com.management.books.model.Books;
import com.management.books.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.management.books.repository.BooksRepository;
import com.management.books.service.BookService;

import java.util.List;
import java.util.UUID;

@Service
public class BooksServiceImpl implements BookService {

    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private SearchRepository searchRepository;

    @Override
    public Books getBookDetail(String id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Override
    public List<Books> getAllBooksDetail(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        Page<Books> pageResult =  booksRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public Books saveBookDetail(Books books) {
        if (books.getId() == null) {
            books.setId(UUID.randomUUID().toString());
        }
        booksRepository.save(books);
        return getBookDetail(books.getId());
    }

    @Override
    public boolean deleteBooksDetail(String bookId) {
        Books data = getBookDetail(bookId);
        if (data != null) {
            booksRepository.delete(data);
            return true;
        }
        return false;
    }

    @Override
    public List<Books> searchBookDetail(String author, String title) {
        if (author != null || title != null) {
            return searchRepository.findByAuthorAndTitle(author, title);
        } else {
            return booksRepository.findAll();
        }
    }


}
