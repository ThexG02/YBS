package com.yourBookStore.book_Service.service;

import com.yourBookStore.book_Service.dto.bookDto;
import com.yourBookStore.book_Service.entity.book;
import com.yourBookStore.book_Service.repository.BookREpository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class bookService {
    private final BookREpository bookREpository;
    private final ModelMapper modelMapper;;


    // get all book
    public List<bookDto> getAllBooks(){
       log.info("fetching all the books in DB");
       List<book> books = bookREpository.findAll();
       return books.stream().map(book -> modelMapper.map(book, bookDto.class))
               .toList();
    }
    // add  a new book
    public bookDto addbook(bookDto bookDto){
        book book = new book();
        BeanUtils.copyProperties(bookDto,book);
        book savedBook=bookREpository.save(book);
        return modelMapper.map(savedBook,bookDto.class);
    }
    //getbook by id
    public bookDto getbookByid(Long id ){
        log.info("getting book by id : {}",id);
        Optional<book> Currbook = bookREpository.findById(id);
        return Currbook
                .map(book -> modelMapper.map(book, bookDto.class))
                .orElseThrow(()-> new RuntimeException("book not found"));
    }

   //Update book
    public bookDto updateBookPrice(Long id , bookDto bookDto){
        book currbook = bookREpository.findById(id).orElseThrow(()-> new RuntimeException("book not fount"));
        currbook.setPrice(bookDto.getPrice());
        book savedBook = bookREpository.save(currbook);
        return modelMapper.map(savedBook, bookDto.class);

    }
    //Delete Book
    public void deletebook(Long id){
        book book = bookREpository.findById(id).orElseThrow(()-> new RuntimeException("book not found"));
        bookREpository.delete(book);

    }


}
