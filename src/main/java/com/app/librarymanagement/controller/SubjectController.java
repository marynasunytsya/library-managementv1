package com.app.librarymanagement.controller;

import com.app.librarymanagement.model.Subject;
import com.app.librarymanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    public List<Subject> list() {
        return subjectService.list();
    }

    @PostMapping("/add")
    public Subject save(@RequestBody Subject Subject) {
        return subjectService.save(Subject);
    }

    @PutMapping("/update")
    public Subject update(@RequestBody Subject Subject) {
        return subjectService.update(Subject);
    }

    @DeleteMapping("/delete/{subjectId}")
    public String delete(@PathVariable(name = "subjectId") Integer SubjectId) {
        subjectService.delete(SubjectId);
        return "Successfully deleted the Subject";
    }

    @GetMapping("/getBy/{subjectId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "subjectId") Integer subjectId) {
        return subjectService.getById(subjectId);
    }
}
