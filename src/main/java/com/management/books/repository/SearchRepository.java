package com.management.books.repository;

import com.management.books.model.Books;

import java.util.List;

public interface SearchRepository {
    List<Books> findByAuthorAndTitle(String author, String title);
}
