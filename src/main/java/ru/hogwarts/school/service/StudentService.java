package ru.hogwarts.school.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.ElemNotFound;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final FacultyMapper facultyMapper;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, FacultyMapper facultyMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.facultyMapper = facultyMapper;
    }

    public StudentRecord createStudent(StudentRecord studentRecord) {
        logger.info("Was invoked method for create new Student");
        logger.debug("Check before create new Student = {}", studentRecord);
        return studentMapper.toRecord(studentRepository.save(studentMapper.toEntity(studentRecord)));
    }

    public StudentRecord findStudentByID(long id) {
        return studentMapper.toRecord(getByID(id));
    }

    public StudentRecord updateStudent(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        logger.info("Was invoked method for updateStudent");
        logger.debug("check before save {}", oldStudent);
        return studentMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudentByID(long id) {
        Student student = getByID(id);
        logger.info("Was invoked method for delete student from db");
        studentRepository.delete(student);
        return studentMapper.toRecord(student);
    }

    public Collection<StudentRecord> getAllStudents() {
        logger.info("Was invoked method for get all student from db");
        return studentMapper.toRecordList(studentRepository.findAll());
    }

    public Collection<StudentRecord> getAllStudentsWithAge(Integer age) {
        logger.info("Was invoked method for get all student by age");
        return studentMapper.toRecordList(studentRepository.getStudentsByAge(age));
    }

    public Collection<StudentRecord> findStudentByAgeBetween(Integer from, Integer to) {
        logger.info("Was invoked method for get all student by age between");
        return studentMapper.toRecordList(studentRepository.findStudentByAgeBetween(from, to));
    }


    public FacultyRecord findFacultyByStudentsId(long studentId) {
        logger.info("Was invoked method for get faculty from student by ID = {}", studentId);
        return facultyMapper.toRecord(getByID(studentId).getFaculty());
    }

    public Integer findCountOfStudents() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.findCountOfStudents();
    }

    public Integer averageOfAge() {
        logger.info("Was invoked method for get average of age");
        return studentRepository.averageOfAge();
    }

    public Collection<StudentRecord> fiveLastOfStudentsByID() {
        logger.info("Was invoked method for get last five students by id");
        return studentMapper.toRecordList(studentRepository.fiveLastOfStudentsByID());
    }

    public Collection<String> sortedNameStudStartWithA() {
        logger.info("Was invoked method for get students which name start with A");
        return studentRepository.findAll()
                .stream()
                .filter(x -> x.getName().startsWith("A"))
                .map(x -> x.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble();
    }

    private Student getByID(long id) {
        logger.info("Was invoked method for find by ID = {}", id);
        logger.error("Elem with id = {}, not found. An exception occurred!", id);
        return studentRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
    }

    public void printNameOfStudent() {


        printStudent(0);
        printStudent(1);

        new Thread(() -> {
            printStudent(2);
            printStudent(3);
        }).start();


        new Thread(() -> {
            printStudent(4);
            printStudent(5);
        }).start();
    }

    private void printStudent(int elemIndex) {
        List<Student> studentList = studentRepository.findAll();
        System.out.println(studentList.get(elemIndex).getName());
    }

    public void printNameOfStudentWithSyn() {


        printStudentWithSyn(0);
        printStudentWithSyn(1);

        new Thread(() -> {
            printStudentWithSyn(2);
            printStudentWithSyn(3);
        }).start();


        new Thread(() -> {
            printStudentWithSyn(4);
            printStudentWithSyn(5);
        }).start();
    }

    private synchronized void printStudentWithSyn(int elemIndex) {
        List<Student> studentList = studentRepository.findAll();
        System.out.println(studentList.get(elemIndex).getName());
    }
}
