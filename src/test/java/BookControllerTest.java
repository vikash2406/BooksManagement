
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.books.controller.BookController;
import com.management.books.model.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.management.books.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    private Books book1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book1 = new Books("Book Title", "Author Name", 2024, "ISBN123");
        book1.setId("1");
    }

    @Test
    void testGetBookDetail() {
        when(bookService.getBookDetail(anyString())).thenReturn(book1);

        Books result = bookController.getBookDetail("1");
        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
        assertEquals("Author Name", result.getAuthor());
    }

    @Test
    void testGetAllBookDetail() {
        List<Books> bookList = new ArrayList<>();
        bookList.add(book1);
        when(bookService.getAllBooksDetail(anyInt(), anyInt())).thenReturn(bookList);

        List<Books> result = bookController.getAllBookDetail(0, 10);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Book Title", result.get(0).getTitle());
    }

    @Test
    void testCreateBookDetail() {
        when(bookService.saveBookDetail(any(Books.class))).thenReturn(book1);

        Books result = bookController.createBookDetail(book1);
        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
    }

    @Test
    void testUpdateBookDetail() {
        when(bookService.saveBookDetail(any(Books.class))).thenReturn(book1);

        Books result = bookController.updateBookDetail("1", book1);
        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
    }

}
