package com.codegym.controller;


import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.BookService;
import com.codegym.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    //Tìm kiếm theo biến s
    @GetMapping("books")
    public ModelAndView listBooks(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByNameContaining(s.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }


//    @GetMapping("/books")
//    public ModelAndView listBooks(){
//        Iterable<Book> books = bookService.findAll();
//        ModelAndView modelAndView = new ModelAndView("/book/list");
//        modelAndView.addObject("books",books);
//        return modelAndView;
//    }

    @GetMapping("/create-book")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/create-book")
    public ModelAndView saveBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("redirect:books");
        //modelAndView.addObject("book",new Book());
        redirectAttributes.addFlashAttribute("message", "New book create successfully!");
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("book", book);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-book")
    public ModelAndView updateBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("redirect:books");
        modelAndView.addObject("book", book);
        redirectAttributes.addFlashAttribute("message", "Book update successfully!");
        return modelAndView;
    }

    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-book")
    public String deletebook(@ModelAttribute("book") Book book) {
        bookService.remove(book.getId());
        return "redirect:books";
    }

    @GetMapping("/view-book/{name}")
    public ModelAndView viewCategory(@PathVariable("name") String name) {
            Iterable<Book> books = bookService.findByName(name);

            ModelAndView modelAndView = new ModelAndView("/book/view");
            modelAndView.addObject("book", new Book());
            modelAndView.addObject("books", books);
            return modelAndView;
        }

}
