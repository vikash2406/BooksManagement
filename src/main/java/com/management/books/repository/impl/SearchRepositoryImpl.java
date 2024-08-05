package com.management.books.repository.impl;

import com.management.books.model.Books;
import com.management.books.repository.SearchRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SearchRepositoryImpl implements SearchRepository {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConverter mongoConverter;


    @Override
    public List<Books> findByAuthorAndTitle(String author, String title) {
        List<Books> list = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("Books");
        MongoCollection<Document> collection = database.getCollection("books");
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match",
//                new Document("author", author)
//        )));
        List<Document> pipeline = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            pipeline.add(new Document("$match", new Document("author", author)));
        }

        if (title != null && !title.isEmpty()) {
            pipeline.add(new Document("$match", new Document("title", title)));
        }

        AggregateIterable<Document> result = collection.aggregate(pipeline);

        result.forEach(doc-> list.add(mongoConverter.read(Books.class, doc)));
        return list;
    }
}
