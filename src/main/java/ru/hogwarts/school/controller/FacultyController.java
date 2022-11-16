package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService service) {
        this.facultyService = service;
    }

    @PostMapping
    public ResponseEntity<FacultyRecord> createFaculty(@RequestBody @Valid FacultyRecord facultyRecord) {
        return ResponseEntity.ok(facultyService.createFaculty(facultyRecord));
    }

    @GetMapping("{id}")
    public ResponseEntity<FacultyRecord> findFacultyByID(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.findFacultyByID(id));
    }

    @GetMapping()
    public ResponseEntity<Collection<FacultyRecord>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/findBycolor")
    public ResponseEntity<Collection<FacultyRecord>> getFacultiesByColorIgnoreCase(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColorIgnoreCase(color));
    }

    @GetMapping("/findByName")
    public ResponseEntity<Collection<FacultyRecord>> getFacultiesByNameIgnoreCase(@RequestParam String name) {
        return ResponseEntity.ok(facultyService.getFacultiesByNameIgnoreCase(name));
    }


    @GetMapping("/findFacultyByColorIgnoreCaseOrNameIgnoreCase")
    public ResponseEntity<Collection<FacultyRecord>> findStudentFaculty(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.findFacultyByColorIgnoreCaseOrNameIgnoreCase(nameOrColor));
    }

    @PutMapping({"{id}"})
    public ResponseEntity<FacultyRecord> updateFaculty(@RequestBody @Valid FacultyRecord facultyRecord,@PathVariable long id) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, facultyRecord));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FacultyRecord> deleteFacultyByID(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.deleteFacultyByID(id));
    }


    @GetMapping("/findStudentByFacultyId")
    public ResponseEntity<Collection<StudentRecord>> findStudentByFaculty_id(@RequestParam long facultyId){
        return ResponseEntity.ok(facultyService.findStudentByFaculty_id(facultyId));
    }

}
