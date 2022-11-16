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
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @Mock
    FacultyMapper facultyMapper;

    @InjectMocks
    StudentService studentService;

    StudentRecord studentTest1 = new StudentRecord(1L, "test", 20, 1,new Faculty());
    StudentRecord studentTest2 = new StudentRecord(2L, "test1", 20, 1,new Faculty());


    @Test
    void createStudentPositiveTest() {
        when(studentService.createStudent(studentTest1)).thenReturn(studentTest1);

        verify(studentRepository, times(1)).save(any());
        assertThat(studentService.createStudent(studentTest1))
                .isEqualTo(studentTest1);
    }

    @Test
    void findStudentByIDPositive() {
        when(studentMapper.toRecord(any(Student.class))).thenReturn(studentTest1);
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Student()));


        assertThat(studentService.findStudentByID(1L))
                .isEqualTo(studentTest1);

    }

    @Test
    void updateStudentPositive() {

        StudentRecord studentTestUpDate = new StudentRecord(1L, "testUp", 22, 1,new Faculty());
        Student newStudent = new Student(1L, "test", 22, 22, new Faculty());
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(newStudent));
        newStudent.setName(studentTestUpDate.getName());
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);
        when(studentMapper.toRecord(any(Student.class))).thenReturn(studentTestUpDate);


        assertThat(studentService.updateStudent(1, studentTestUpDate))
                .hasFieldOrPropertyWithValue("name", "testUp");


    }

    @Test
    void updateStudentNegative() {
        assertThatExceptionOfType(ElemNotFound.class)
                .isThrownBy(() -> studentService.updateStudent(50, studentTest1));
    }

    @Test
    void deleteStudentByIDPositive() {
        Student newStudent = new Student(1L, "test", 22, 22, new Faculty());
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(newStudent));
        doNothing().when(studentRepository).delete(newStudent);
        when(studentMapper.toRecord(any(Student.class))).thenReturn(studentTest1);
        studentService.deleteStudentByID(1L);

        verify(studentRepository, times(1)).delete(newStudent);

    }

    @Test
    void deleteStudentByIDNegative() {
        assertThatExceptionOfType(ElemNotFound.class)
                .isThrownBy(() -> studentService.deleteStudentByID(50));
    }

    @Test
    void getAllStudents() {
        assertThat(studentService.getAllStudents())
                .isNotNull();
    }

    @Test
    void getAllStudentsByAge() {
        ArrayList<Student> arr = new ArrayList<>();
        arr.add(new Student());
        arr.add(new Student());
        doReturn(arr).when(studentRepository).getStudentsByAge(anyInt());
        Collection<StudentRecord> collection = new ArrayList<>();
        collection.add(studentTest1);
        collection.add(studentTest2);
        when(studentMapper.toRecordList(anyCollection())).thenReturn(collection);

        assertThat(studentService.getAllStudentsWithAge(20))
                .extracting(StudentRecord::getAge)
                .containsOnly(20);
    }

    @Test
    void findStudentByAgeBetween() {
        ArrayList<Student> arr = new ArrayList<>();
        arr.add(new Student());
        arr.add(new Student());
        doReturn(arr).when(studentRepository).findStudentByAgeBetween(anyInt(), anyInt());

        Collection<StudentRecord> collection = new ArrayList<>();
        collection.add(studentTest1);
        collection.add(studentTest2);
        when(studentMapper.toRecordList(anyCollection())).thenReturn(collection);

        assertThat(studentService.findStudentByAgeBetween(19, 21))
                .extracting(StudentRecord::getAge)
                .containsAnyOf(19, 20, 21);
    }

    @Test
    void findFacultyByStudentsId() {
        Faculty faculty = new Faculty(1L, "Griffengor", "Red");
        Student newStudent = new Student(1L, "test", 22, 22, faculty);

        doReturn(Optional.of(newStudent)).when(studentRepository).findById(anyLong());
        when(facultyMapper.toRecord(faculty))
                .thenReturn( new FacultyRecord(1L, "Griffengor", "Red"));
        assertThat(studentService.findFacultyByStudentsId(1).getName())
                .isEqualTo("Griffengor");
    }

}








