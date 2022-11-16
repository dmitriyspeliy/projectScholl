package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.ElemNotFound;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    FacultyRepository facultyRepository;

    @Mock
    FacultyMapper facultyMapper;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    FacultyService facultyService;

    FacultyRecord correctFaculty1 = new FacultyRecord(1L, "Griffendor", "Red");
    FacultyRecord correctFaculty2 = new FacultyRecord(1L, "Slytherin", "Red");

    @Test
    void createFacultyPositiveTest() {
        when(facultyService.createFaculty(correctFaculty1)).thenReturn(correctFaculty1);

        verify(facultyRepository, times(1)).save(any());
        assertThat(facultyService.createFaculty(correctFaculty1))
                .isEqualTo(correctFaculty1);
    }

    @Test
    void findFacultyByIDPositive() {
        when(facultyMapper.toRecord(any(Faculty.class))).thenReturn(correctFaculty1);
        when(facultyRepository.findById(correctFaculty1.getId()))
                .thenReturn(Optional.of(new Faculty()));


        assertThat(facultyService.findFacultyByID(1))
                .isEqualTo(correctFaculty1);

    }

    @Test
    void updateFacultyPositive() {

        FacultyRecord facultyTestUpDate = new FacultyRecord(1L, "testUp", "test");
        Faculty newFaculty = new Faculty(1L, "test", "test");
        when(facultyRepository.findById(1L))
                .thenReturn(Optional.of(newFaculty));
        newFaculty.setName(facultyTestUpDate.getName());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(newFaculty);
        when(facultyMapper.toRecord(any(Faculty.class))).thenReturn(facultyTestUpDate);


        assertThat(facultyService.updateFaculty(1, facultyTestUpDate))
                .hasFieldOrPropertyWithValue("name", "testUp");


    }

    @Test
    void updateFacultyNegative() {
        assertThatExceptionOfType(ElemNotFound.class)
                .isThrownBy(() -> facultyService.updateFaculty(50, correctFaculty1));
    }

    @Test
    void deleteFacultyByIDPositive() {
        Faculty newFaculty = new Faculty(1L, "test", "test");
        when(facultyRepository.findById(1L))
                .thenReturn(Optional.of(newFaculty));
        doNothing().when(facultyRepository).delete(newFaculty);
        when(facultyMapper.toRecord(any(Faculty.class))).thenReturn(correctFaculty1);
        facultyService.deleteFacultyByID(1L);

        verify(facultyRepository, times(1)).delete(newFaculty);

    }

    @Test
    void deleteStudentByIDNegative() {
        assertThatExceptionOfType(ElemNotFound.class)
                .isThrownBy(() -> facultyService.deleteFacultyByID(50));
    }

    @Test
    void getAllStudents() {
        assertThat(facultyService.getAllFaculties())
                .isNotNull();
    }

    @Test
    void getFacultiesByColorIgnoreCase() {
        ArrayList<Faculty> arr = new ArrayList<>();
        arr.add(new Faculty());
        arr.add(new Faculty());
        doReturn(arr).when(facultyRepository).getFacultiesByColorIgnoreCase(anyString());
        Collection<FacultyRecord> collection = new ArrayList<>();
        collection.add(correctFaculty1);
        collection.add(correctFaculty2);
        when(facultyMapper.toRecordList(anyCollection())).thenReturn(collection);

        assertThat(facultyService.getFacultiesByColorIgnoreCase("Red"))
                .extracting(FacultyRecord::getColor)
                .containsOnly("Red");
    }

    @Test
    void getFacultiesByNameIgnoreCase() {
        ArrayList<Faculty> arr = new ArrayList<>();
        arr.add(new Faculty());
        arr.add(new Faculty());
        doReturn(arr).when(facultyRepository).getFacultiesByNameIgnoreCase(anyString());
        Collection<FacultyRecord> collection = new ArrayList<>();
        collection.add(correctFaculty1);
        collection.add(correctFaculty2);
        when(facultyMapper.toRecordList(anyCollection())).thenReturn(collection);

        assertThat(facultyService.getFacultiesByNameIgnoreCase("Griffendor"))
                .extracting(FacultyRecord::getName)
                .containsAnyOf("Griffendor");
    }

    @Test
    void findStudentByFaculty_id() {
        Student studentTest1 = new Student(1L, "test", 20, 1, new Faculty());
        Student studentTest2 = new Student(2L, "test1", 20, 1, new Faculty());
        Set<Student> arr = new HashSet<>();
        arr.add(studentTest1);
        arr.add(studentTest2);
        Faculty faculty = new Faculty(1L, "Griffengor", "Red", arr);


        StudentRecord studentRecordTest1 = new StudentRecord(1L, "test", 20, 1,faculty);
        StudentRecord studentRecordTest2 = new StudentRecord(2L, "test1", 20, 1,faculty);

        doReturn(Optional.of(faculty)).when(facultyRepository).findById(anyLong());
        Collection<StudentRecord> collection = new ArrayList<>();
        collection.add(studentRecordTest1);
        collection.add(studentRecordTest2);
        when(studentMapper.toRecordList(anyCollection())).thenReturn(collection);
        assertThat(facultyService.findStudentByFaculty_id(1))
                .extracting(StudentRecord::getFaculty_id)
                .containsOnly(1);
    }

}

