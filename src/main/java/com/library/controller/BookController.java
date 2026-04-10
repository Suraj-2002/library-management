package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping("/borrow/{id}")
    public Book borrow(@PathVariable String id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return bookService.borrowBook(id, email);
    }

    @PostMapping("/return/{id}")
    public Book returnBook(@PathVariable String id) {
        return bookService.returnBook(id);
    }

    @GetMapping("/my")
    public List<Book> myBooks(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return bookService.getUserBooks(email);
    }
}