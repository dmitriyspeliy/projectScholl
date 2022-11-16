package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class SchoolApplicationWithMockTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    FacultyMapper facultyMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private final Long id = 30L;
    private final String name = "MyTest";
    private final String color = "Orange";

    private final JSONObject facultyObject = new JSONObject();

    private final FacultyRecord facultyRecord = new FacultyRecord();

    private final FacultyRecord facultyRecord1 = new FacultyRecord();

    private final Collection<FacultyRecord> collection = new ArrayList<>();

    private final ArrayList<Faculty> arr = new ArrayList<>();

    private final Faculty faculty = new Faculty();

    @BeforeEach
    void init() {
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        facultyRecord.setId(id);
        facultyRecord.setName(name);
        facultyRecord.setColor(color);
        collection.add(facultyRecord);
        collection.add(facultyRecord1);
        faculty.setName("MyTest");
        faculty.setColor("Orange");
        arr.add(faculty);
        arr.add(new Faculty());
        faculty.setId(30L);

    }

    @Test
    void createFaculty() throws Exception {

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty") //send
                        .content(facultyObject.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.name").value(name)).andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void findFacultyByID() throws Exception {

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/2") //send
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id));

    }

    @Test
    void getAllFaculties() throws Exception {

        when(facultyRepository.findAll()).thenReturn(arr);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty") //send
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void getFacultiesByColorIgnoreCase() throws Exception {

        when(facultyRepository.getFacultiesByColorIgnoreCase(anyString())).thenReturn(arr);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/findBycolor?color=orange") //send
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$[0].color", equalTo("Orange")));
    }

    @Test
    void getFacultiesByNameIgnoreCase() throws Exception {

        when(facultyRepository.getFacultiesByNameIgnoreCase(anyString())).thenReturn(arr);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/findByName?name=mytest") //send
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$[0].name", equalTo("MyTest")));
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty updateFaculty = new Faculty();
        updateFaculty.setColor("Blue");
        updateFaculty.setName("Blue");

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updateFaculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/30") //send
                        .content(facultyObject.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.name").value(updateFaculty.getName())).andExpect(jsonPath("$.color").value(updateFaculty.getColor()));

    }

    @Test
    void deleteFacultyByID() throws Exception {

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(facultyService.deleteFacultyByID(anyLong())).thenReturn(facultyRecord);


        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/2") //send
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.name").value(name));

    }


}
