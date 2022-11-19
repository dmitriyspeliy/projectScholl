package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRecord> createStudent(@RequestBody @Valid StudentRecord studentRecord) {
        return ResponseEntity.ok(studentService.createStudent(studentRecord));
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentRecord> findStudentByID(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findStudentByID(id));
    }

    @GetMapping()
    public ResponseEntity<Collection<StudentRecord>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/getWithAge")
    public ResponseEntity<Collection<StudentRecord>> getAllStudentsWithAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentService.getAllStudentsWithAge(age));
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentRecord> updateStudent(@RequestBody @Valid StudentRecord studentRecord, @PathVariable long id) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentRecord));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StudentRecord> deleteStudentByID(@PathVariable long id) {
        return ResponseEntity.ok(studentService.deleteStudentByID(id));
    }


    @GetMapping("/findStudentFaculty/{studentId}")
    public ResponseEntity<FacultyRecord> findStudentFaculty(@PathVariable long studentId) {
        return ResponseEntity.ok(studentService.findFacultyByStudentsId(studentId));
    }


    @GetMapping("/getWithAgeBetween")
    public ResponseEntity<Collection<StudentRecord>> findStudentByAgeBetween(@RequestParam Integer from,
                                                                             @RequestParam Integer to) {
        return ResponseEntity.ok(studentService.findStudentByAgeBetween(from, to));
    }

    @GetMapping("/findCountOfStudents")
    public ResponseEntity<Integer> findCountOfStudents() {
        return ResponseEntity.ok(studentService.findCountOfStudents());
    }

    @GetMapping("/averageOfAge")
    public ResponseEntity<Integer> averageOfAge() {
        return ResponseEntity.ok(studentService.averageOfAge());
    }

    @GetMapping("/fiveLastOfStudentsByID")
    public ResponseEntity<Collection<StudentRecord>> fiveLastOfStudentsByID() {
        return ResponseEntity.ok(studentService.fiveLastOfStudentsByID());
    }

    @GetMapping("/sortedNameStudStartWithA")
    public ResponseEntity<Collection<String>> sortedNameStudStartWithA() {
        return ResponseEntity.ok(studentService.sortedNameStudStartWithA());
    }

    @GetMapping("/getAverageAgeOfStudents")
    public ResponseEntity<Double> getAverageAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudents());
    }


    @GetMapping("printNameOfStudent")
    public ResponseEntity<Void> printNameOfStudent() {
        studentService.printNameOfStudent();
        return null;
    }

    @GetMapping("printNameOfStudentWithSyn")
    public ResponseEntity<Void> printNameOfStudentWithSyn() {
        studentService.printNameOfStudentWithSyn();
        return null;
    }



}
