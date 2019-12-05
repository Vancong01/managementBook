package com.codegym.service;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public interface BookService {
    Iterable<Book> findAll();
    Book findById(Long id);
    void save(Book book);
    void remove(Long id);

    Iterable<Book> findAllByCategory(Category category);


    Iterable<Book> findByName(String name);

    Page<Book> findAll(Pageable pageable);
    Page<Book> findAllByNameContaining(String name,Pageable pageable);

}
