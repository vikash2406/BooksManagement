package model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Books {
    @Id
    private String id;

    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private Integer publishYear;
    @NonNull
    private String isbn;

    public Books(@NonNull String title, @NonNull String author, @NonNull Integer publishYear, @NonNull String isbn) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isbn = isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

