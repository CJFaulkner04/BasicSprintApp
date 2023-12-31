package com.example.SpringApp.controller;

import com.example.SpringApp.model.Author;
import com.example.SpringApp.model.Book;
import com.example.SpringApp.repository.AuthorRepository;
import com.example.SpringApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        Author author = book.getAuthor();
        authorRepository.save(author);

        String title = book.getTitle();
        book.setTitle(title);

        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }

        Author updatedAuthor = updatedBook.getAuthor();
        if (updatedAuthor != null && updatedAuthor.getName() != null) {
            Author author = book.getAuthor();
            author.setName(updatedAuthor.getName());
            authorRepository.save(author);
        }

        bookRepository.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book);
        return "book-edit"; // Updated template name to match the HTML file
    }



}
