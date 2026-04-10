package com.library.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;
    private String author;

    private boolean available;

    private String borrowedBy;

    private LocalDateTime borrowedAt;

    private LocalDateTime expiryTime; // for expiry books

    private boolean libraryOnly; // 10 PM return policy
}