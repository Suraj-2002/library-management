package com.library.scheduler;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookScheduler {

    private final BookService bookService;

    public BookScheduler(BookService bookService) {
        this.bookService = bookService;
    }

    // ✅ runs every minute to check expired books
    @Scheduled(fixedRate = 60000)
    public void autoReturnExpiredBooks() {
        List<Book> borrowedBooks = bookService.getBorrowedBooks();
        LocalDateTime now = LocalDateTime.now();

        for (Book book : borrowedBooks) {
            if (book.getExpiryTime() != null && now.isAfter(book.getExpiryTime())) {
                bookService.resetBook(book);
                bookService.saveBook(book);
                System.out.println("Auto-returned: " + book.getTitle());
            }
        }
    }
}