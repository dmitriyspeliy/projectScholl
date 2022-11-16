package ru.hogwarts.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import java.util.Map;

@Controller
@RequestMapping("mainStudents")
public class ModelAndViewControllerForStudents {
    private final StudentService studentService;

    static final ModelAndView MODELANDVIEW = new ModelAndView("mainStudents");


    public ModelAndViewControllerForStudents(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ModelAndView getStudentsModel(@RequestParam(value = "age", required = false) String age,
                                         @RequestParam(value = "from", required = false) String from,
                                         @RequestParam(value = "to", required = false) String to) {
        if (age == null && to == null && from == null) {
            MODELANDVIEW.addObject("student", new StudentRecord());
            MODELANDVIEW.addObject("students", studentService.getAllStudents());
        } else if(age!=null){
            MODELANDVIEW.addObject("student", new StudentRecord());
            MODELANDVIEW.addObject("students", studentService.getAllStudentsWithAge(Integer.valueOf(age)));
        }else if(to!=null && from!=null){
            MODELANDVIEW.addObject("student", new StudentRecord());
            MODELANDVIEW.addObject("students", studentService.
                    findStudentByAgeBetween(Integer.valueOf(from),Integer.valueOf(to)));
        }
        return MODELANDVIEW;
    }

    @PostMapping()
    public String saveStudent(@RequestParam Map<String, String> map) {
        StudentRecord studentRecord = createStudent(map);
        studentService.createStudent(studentRecord);
        return "redirect:/mainStudents";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") int id) {
        studentService.deleteStudentByID(id);
        return "redirect:/mainStudents";
    }

    @GetMapping("/updateStudent/{id}")
    public String updateStudents(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("updateStudent", studentService.findStudentByID(id));
        return "updateStudent";
    }

    @PostMapping("/updateAndSaveStudent/{id}")
    public String updateAndSaveSight(@RequestParam Map<String, String> map,
                                     @PathVariable(value = "id") long id) {
        StudentRecord studentRecord = createStudent(map);
        studentRecord.setId(id);
        studentService.updateStudent(id,studentRecord);
        return "redirect:/mainStudents";
    }

    private StudentRecord createStudent(Map<String, String> map) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setName(map.get("name"));
        studentRecord.setAge(Integer.parseInt(map.get("age")));
        int faculty_id = switch (map.get("type")) {
            case ("Gryffindor") -> 1;
            case ("Hufflepuff") -> 2;
            case ("Ravenclaw") -> 3;
            case ("Slytherin") -> 4;
            default -> throw new IllegalStateException("Unexpected value");
        };
        studentRecord.setFaculty_id(faculty_id);
        return studentRecord;
    }


}
