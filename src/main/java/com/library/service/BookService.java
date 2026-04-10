package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedAt(null);
        book.setExpiryTime(null);
        return Objects.requireNonNull(bookRepository.save(book));
    }

    @SuppressWarnings("null")
    public Book borrowBook(@NonNull String bookId, String email) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        book.setAvailable(false);
        book.setBorrowedBy(email);
        book.setBorrowedAt(LocalDateTime.now());

        if (book.getExpiryMinutes() != null) {
            book.setExpiryTime(LocalDateTime.now().plusMinutes(book.getExpiryMinutes()));
        }

        if (book.isLibraryOnly()) {
            LocalDateTime tenPM = LocalDateTime.now()
                    .withHour(22)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            book.setExpiryTime(tenPM);
        }

        return Objects.requireNonNull(bookRepository.save(book));
    }

    @SuppressWarnings("null")
    public Book returnBook(@NonNull String bookId, String email) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!email.equals(book.getBorrowedBy())) {
            throw new RuntimeException("You did not borrow this book");
        }

        resetBook(book);
        return Objects.requireNonNull(bookRepository.save(book));
    }

    public void resetBook(Book book) {
        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedAt(null);
        book.setExpiryTime(null);
    }

    public List<Book> getUserBooks(String email) {
        return bookRepository.findByBorrowedBy(email);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBorrowedBooks() {
        return bookRepository.findByAvailableFalse();
    }

    public void saveBook(@NonNull Book book) {
        Objects.requireNonNull(bookRepository.save(book));
    }
}