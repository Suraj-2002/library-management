package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ✅ ADMIN only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // ✅ Both ADMIN and USER
    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // ✅ USER only
    @SuppressWarnings("null")
    @PostMapping("/borrow/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> borrow(@PathVariable @NonNull String id, HttpServletRequest request) {
        try {
            String email = Objects.requireNonNull(
                (String) request.getAttribute("email"),
                "Email not found in request"
            );
            return ResponseEntity.ok(bookService.borrowBook(id, email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ USER only
    @SuppressWarnings("null")
    @PostMapping("/return/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> returnBook(@PathVariable @NonNull String id, HttpServletRequest request) {
        try {
            String email = Objects.requireNonNull(
                (String) request.getAttribute("email"),
                "Email not found in request"
            );
            return ResponseEntity.ok(bookService.returnBook(id, email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ USER only
    @SuppressWarnings("null")
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Book>> myBooks(HttpServletRequest request) {
        String email = Objects.requireNonNull(
            (String) request.getAttribute("email"),
            "Email not found in request"
        );
        return ResponseEntity.ok(bookService.getUserBooks(email));
    }
}