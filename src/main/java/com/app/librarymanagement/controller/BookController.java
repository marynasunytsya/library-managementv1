package com.app.librarymanagement.controller;

import com.app.librarymanagement.model.Book;
import com.app.librarymanagement.model.Subject;
import com.app.librarymanagement.service.BookService;
import com.app.librarymanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final SubjectService subjectService;

    @Autowired
    public BookController(BookService bookService, SubjectService subjectService) {
        this.bookService = bookService;
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    public List<Book> list() {
        return bookService.list();
    }

    @PostMapping("/add")
    public Book save(@RequestBody Book book) {
        for (Subject sub : book.getSubjects()) {
            subjectService.save(sub);
        }
        return bookService.save(book);
    }

    @PutMapping("/update")
    public Book update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("/delete/{bookId}")
    public String delete(@PathVariable(name = "bookId") Integer bookId) {
        bookService.delete(bookId);
        return "Successfully deleted the book";
    }

    @GetMapping("/getBy/{bookId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "bookId") Integer bookId) {
        return bookService.getById(bookId);
    }

    @GetMapping("/bySubjectId/{subjectId}")
    public ResponseEntity<Object> getAllBooksBySubjectId(@PathVariable(name = "subjectId") Integer subjectId) {
        return bookService.getAllBooksBySubjectId(subjectId);
    }
}
