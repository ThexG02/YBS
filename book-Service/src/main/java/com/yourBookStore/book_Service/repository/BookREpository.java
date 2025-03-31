package com.yourBookStore.book_Service.repository;

import com.yourBookStore.book_Service.entity.book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookREpository extends JpaRepository<book, Long> {
}
