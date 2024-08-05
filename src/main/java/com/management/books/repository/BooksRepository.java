package com.management.books.repository;

import com.management.books.model.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
    List<Books> findByTitleOrAuthor(String query);
}
