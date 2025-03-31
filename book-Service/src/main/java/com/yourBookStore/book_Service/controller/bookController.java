package com.yourBookStore.book_Service.controller;

import com.yourBookStore.book_Service.dto.bookDto;
import com.yourBookStore.book_Service.service.bookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/book")
@RequiredArgsConstructor
public class bookController {
    private final bookService bookService;

    @GetMapping
    public ResponseEntity<List<bookDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<bookDto> getBookByid(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getbookByid(id));
    }
    @PostMapping
    public ResponseEntity<bookDto> addbook(@RequestBody bookDto bookDto){
        return ResponseEntity.ok(bookService.addbook(bookDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<bookDto>updateBook(@PathVariable Long id , @RequestBody bookDto bookDto){
        return ResponseEntity.ok(bookService.updateBookPrice(id,bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deletebook(id);
        return ResponseEntity.noContent().build();
    }


}
