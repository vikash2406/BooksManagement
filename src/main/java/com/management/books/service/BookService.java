package com.management.books.service;


import com.management.books.model.Books;

import java.util.List;

public interface BookService {
    Books getBookDetail(String id);

    List<Books> getAllBooksDetail(int page, int size);

    Books saveBookDetail(Books books);

    boolean deleteBooksDetail(String bookId);

    List<Books> searchBookDetail(String author, String title) ;
}

