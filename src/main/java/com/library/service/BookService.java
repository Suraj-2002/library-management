package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book borrowBook(String bookId, String email) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Already borrowed");
        }

        book.setAvailable(false);
        book.setBorrowedBy(email);
        book.setBorrowedAt(LocalDateTime.now());

        // expiry logic (example: 1 hour)
        if (book.getExpiryTime() == null) {
            book.setExpiryTime(LocalDateTime.now().plusHours(1));
        }

        return bookRepository.save(book);
    }

    public Book returnBook(String bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedAt(null);
        book.setExpiryTime(null);

        return bookRepository.save(book);
    }

    public List<Book> getUserBooks(String email) {
        return bookRepository.findByBorrowedBy(email);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}