package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;


import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTests {

    @LocalServerPort
    private int port;

    private String URL;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void init(){
        URL = "http://localhost:" + this.port + "/student";
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        assertThat(this.restTemplate.getForObject(URL, Collection.class))
                .isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setName("Vasya Petrov");
        studentRecord.setAge(28);
        studentRecord.setFaculty_id(1);
        assertThat(this.restTemplate.postForObject(URL, studentRecord, StudentRecord.class))
                .isNotNull();
    }

    @Test
    public void testFindStudentById() throws Exception {
        assertThat(this.restTemplate.getForObject(URL + "/2", StudentRecord.class))
                .isNotNull();
    }

    @Test
    public void testGetAllStudentsWithAge() throws Exception {
        assertThat(this.restTemplate.getForObject(URL + "/getWithAge?age=20", Collection.class))
                .isNotNull();
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setName("Vasya Petrov");
        studentRecord.setAge(27);
        studentRecord.setFaculty_id(1);
        String url = URL + "/{id}";

        HttpEntity<StudentRecord> request = new HttpEntity<>(studentRecord);
        ResponseEntity<StudentRecord> response =
                restTemplate.exchange(url,HttpMethod.PUT, request, StudentRecord.class,2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void testDeleteByID() throws Exception {
        String url = URL + "/{id}";
        StudentRecord studentRecordEntity = studentController.findStudentByID(1).getBody();
        HttpEntity<StudentRecord> request = new HttpEntity<>(studentRecordEntity);


        ResponseEntity<StudentRecord> response =
                restTemplate.exchange(url,HttpMethod.DELETE, request, StudentRecord.class,1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFindStudentFaculty() throws Exception {
        assertThat(this.restTemplate.getForObject(URL + "/findStudentFaculty/2", StudentRecord.class))
                .isNotNull();
    }


    @Test
    public void findStudentByAgeBetween() throws Exception {
        assertThat(this.restTemplate.getForObject(URL + "/getWithAgeBetween?from=20&to=40", Collection.class))
                .isNotNull();
    }



}