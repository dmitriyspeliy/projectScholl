package ru.hogwarts.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.service.FacultyService;

import java.util.Map;

@Controller
@RequestMapping("mainFaculties")
public class ModelAndViewControllerForFaculties {
    private final FacultyService facultyService;

    static final ModelAndView MODELANDVIEW = new ModelAndView("mainFaculty");


    public ModelAndViewControllerForFaculties(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping()
    public ModelAndView getFacultiesModel(@RequestParam(value = "color",required = false) String color,
                                          @RequestParam(value = "name",required = false) String name) {
        if (color == null && name == null) {
            MODELANDVIEW.addObject("faculty", new FacultyRecord());
            MODELANDVIEW.addObject("faculties", facultyService.getAllFaculties());
        } else if(color!=null) {
            MODELANDVIEW.addObject("faculty", new FacultyRecord());
            MODELANDVIEW.addObject("faculties", facultyService.getFacultiesByColorIgnoreCase(color));
        }else {
            MODELANDVIEW.addObject("faculty", new FacultyRecord());
            MODELANDVIEW.addObject("faculties", facultyService.getFacultiesByNameIgnoreCase(name));
        }
        return MODELANDVIEW;
    }

    @PostMapping()
    public String saveFaculty(@RequestParam Map<String,String> map) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setName(map.get("name"));
        facultyRecord.setColor(map.get("color"));
        facultyService.createFaculty(facultyRecord);
        return "redirect:/mainFaculties";
    }

    @GetMapping("/deleteFaculty/{id}")
    public String deleteFaculty(@PathVariable(value = "id") int id) {
        facultyService.deleteFacultyByID(id);
        return "redirect:/mainFaculties";
    }

    @GetMapping("/updateFaculty/{id}")
    public String updateFaculty(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("updateFaculty", facultyService.findFacultyByID(id));
        return "updateFaculty";
    }

    @PostMapping("/updateAndSaveFaculty/{id}")
    public String updateAndSaveSight(@RequestParam Map<String,String> map,
                                     @PathVariable(value = "id") long id) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setName(map.get("name"));
        facultyRecord.setColor(map.get("color"));
        facultyRecord.setId(id);
        facultyService.updateFaculty(id,facultyRecord);
        return "redirect:/mainFaculties";
    }



}
