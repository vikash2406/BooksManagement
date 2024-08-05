import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.management.books.model.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import com.management.books.repository.BooksRepository;
import com.management.books.service.impl.BooksServiceImpl;

class BooksServiceImplTest {

    @InjectMocks
    private BooksServiceImpl booksService;

    @Mock
    private BooksRepository booksRepository;

    private Books book1;
    private Books book2;
    private List<Books> bookList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = new Books("Book 1", "Author 1", 2021, "ISBN0001");
        book2 = new Books("Book 2", "Author 2", 2022, "ISBN0002");
        bookList = Arrays.asList(book1, book2);
    }

    @Test
    void testGetBookDetail() {
        when(booksRepository.findById(anyString())).thenReturn(Optional.of(book1));

        Books result = booksService.getBookDetail("ISBN0001");
        assertNotNull(result);
        assertEquals(book1.getTitle(), result.getTitle());
        verify(booksRepository, times(1)).findById("ISBN0001");
    }

    @Test
    void testGetBookDetailNotFound() {
        when(booksRepository.findById(anyString())).thenReturn(Optional.empty());

        Books result = booksService.getBookDetail("ISBN0001");
        assertNull(result);
        verify(booksRepository, times(1)).findById("ISBN0001");
    }

    @Test
    void testGetAllBooksDetail() {
        PageRequest paging = PageRequest.of(0, 10);
        Page<Books> pageResult = new PageImpl<>(bookList);

        when(booksRepository.findAll(paging)).thenReturn(pageResult);

        List<Books> result = booksService.getAllBooksDetail(0, 10);
        assertNotNull(result);
        assertEquals(bookList.size(), result.size());
        verify(booksRepository, times(1)).findAll(paging);
    }

    @Test
    void testSaveBookDetailNew() {
        Books newBook = new Books("New Book", "New Author", 2024, "ISBN0003");

        String generatedId = "new-uuid";
        Books savedBook = new Books(newBook.getTitle(), newBook.getAuthor(), newBook.getPublishYear(), newBook.getIsbn());
        savedBook.setId(generatedId);

        when(booksRepository.save(any(Books.class))).thenAnswer(invocation -> {
            Books bookToSave = invocation.getArgument(0);
            bookToSave.setId(generatedId);
            return bookToSave;
        });


        when(booksRepository.findById(generatedId)).thenReturn(Optional.of(savedBook));

        Books result = booksService.saveBookDetail(newBook);


        assertNotNull(result);
        assertEquals(generatedId, result.getId());
        assertEquals(newBook.getTitle(), result.getTitle());
        assertEquals(newBook.getAuthor(), result.getAuthor());
        assertEquals(newBook.getPublishYear(), result.getPublishYear());
        assertEquals(newBook.getIsbn(), result.getIsbn());

        verify(booksRepository, times(1)).save(any(Books.class));
        verify(booksRepository, times(1)).findById(generatedId);
    }



    @Test
    void testSaveBookDetailExisting() {
        book1.setId("existing-id");
        when(booksRepository.save(any(Books.class))).thenReturn(book1);
        when(booksRepository.findById(anyString())).thenReturn(Optional.of(book1));

        Books result = booksService.saveBookDetail(book1);
        assertNotNull(result);
        assertEquals("existing-id", result.getId());
        verify(booksRepository, times(1)).save(any(Books.class));
        verify(booksRepository, times(1)).findById("existing-id");
    }

    @Test
    void testDeleteBooksDetail() {
        when(booksRepository.findById(anyString())).thenReturn(Optional.of(book1));

        boolean result = booksService.deleteBooksDetail("ISBN0001");
        assertTrue(result);
        verify(booksRepository, times(1)).delete(book1);
    }

    @Test
    void testDeleteBooksDetailNotFound() {
        when(booksRepository.findById(anyString())).thenReturn(Optional.empty());

        boolean result = booksService.deleteBooksDetail("ISBN0001");
        assertFalse(result);
        verify(booksRepository, never()).delete(any(Books.class));
    }

    @Test
    void testSearchBookDetail() {
        when(booksRepository.findByTitleOrAuthor(anyString())).thenReturn(bookList);

        List<Books> result = booksService.searchBookDetail("Book");
        assertNotNull(result);
        assertEquals(bookList.size(), result.size());
        verify(booksRepository, times(1)).findByTitleOrAuthor("Book");
    }
}
