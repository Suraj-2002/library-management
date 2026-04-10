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

    // null = no expiry, set in minutes by admin
    private Integer expiryMinutes;

    // calculated at borrow time
    private LocalDateTime expiryTime;

    // true = return at 10PM (library only policy)
    private boolean libraryOnly;
}