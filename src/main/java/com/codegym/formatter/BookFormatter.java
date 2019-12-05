package com.codegym.formatter;

import com.codegym.model.Book;
import com.codegym.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class BookFormatter implements Formatter<Book> {


    //Chuyển đổi từ id sang object
    private BookService bookService;

    @Autowired
    public BookFormatter(BookService bookService){
        this.bookService = bookService;
    }
    @Override
    public Book parse(String text, Locale locale) throws ParseException {
        return bookService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Book object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}