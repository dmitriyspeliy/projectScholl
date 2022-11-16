package ru.hogwarts.school.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.ElemNotFound;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
@Getter
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final FacultyMapper facultyMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, FacultyMapper facultyMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.facultyMapper = facultyMapper;
    }

    public StudentRecord createStudent(StudentRecord studentRecord) {
        return studentMapper.toRecord(studentRepository.save(studentMapper.toEntity(studentRecord)));
    }

    public StudentRecord findStudentByID(long id) {
        return studentMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id)));
    }

    public StudentRecord updateStudent(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        return studentMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudentByID(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
        studentRepository.delete(student);
        return studentMapper.toRecord(student);
    }

    public Collection<StudentRecord> getAllStudents() {
        return studentMapper.toRecordList(studentRepository.findAll());
    }

    public Collection<StudentRecord> getAllStudentsWithAge(Integer age) {
        return studentMapper.toRecordList(studentRepository.getStudentsByAge(age));
    }

    public Collection<StudentRecord> findStudentByAgeBetween(Integer from, Integer to) {
        return studentMapper.toRecordList(studentRepository.findStudentByAgeBetween(from, to));
    }


    public FacultyRecord findFacultyByStudentsId(long studentId) {
        return facultyMapper.toRecord(studentRepository.findById(studentId).get().getFaculty());
    }

    public Integer findCountOfStudents() {
        return studentRepository.findCountOfStudents();
    }

    public Integer averageOfAge() {
        return studentRepository.averageOfAge();
    }

    public Collection<StudentRecord> fiveLastOfStudentsByID() {
        return studentMapper.toRecordList(studentRepository.fiveLastOfStudentsByID());
    }
}
