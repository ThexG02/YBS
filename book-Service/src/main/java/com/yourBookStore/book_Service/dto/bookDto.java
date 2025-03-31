package com.yourBookStore.book_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bookDto {
    private Long id;
    private String title ;
    private String genre;
    private String author;
    private Double price;
}
