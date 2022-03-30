package com.app.librarymanagement.service;

import com.app.librarymanagement.model.Book;
import com.app.librarymanagement.model.Subject;
import com.app.librarymanagement.repository.BookRepository;
import com.app.librarymanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final BookRepository bookRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, BookRepository bookRepository) {
        this.subjectRepository = subjectRepository;
        this.bookRepository = bookRepository;
    }

    public List<Subject> list() {
        return subjectRepository.findAll();
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject update(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void delete(Integer subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isPresent()) {
            List<Book> books = bookRepository.findAll();
            for (Book book:books
                 ) {
                for (Subject sub: book.getSubjects()
                     ) {
                    if(sub.equals(subject.get())){
                        book.getSubjects().remove(sub);
                        break;
                    }
                }
            }
            subjectRepository.delete(subject.get());
        } else {
            throw new RuntimeException("Subject doesn't exists against this ID");
        }
    }

    public ResponseEntity<Object> getById(Integer subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isPresent()) {
            return new ResponseEntity<>(subject.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no subject against this subject ID", HttpStatus.OK);
        }
    }
}
