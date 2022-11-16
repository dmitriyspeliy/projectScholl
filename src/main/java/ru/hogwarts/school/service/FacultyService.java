package ru.hogwarts.school.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.ElemNotFound;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
@Getter
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    private final StudentMapper studentMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
        this.studentMapper = studentMapper;
    }

    public FacultyRecord createFaculty(FacultyRecord facultyRecord) {
        return facultyMapper.toRecord(facultyRepository.save(facultyMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord findFacultyByID(long id) {
        return facultyMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id)));
    }

    public FacultyRecord updateFaculty(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return facultyMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord deleteFacultyByID(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
        facultyRepository.delete(faculty);
        return facultyMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> getAllFaculties() {
        return facultyMapper.toRecordList(facultyRepository.findAll());
    }

    public Collection<FacultyRecord> getFacultiesByColorIgnoreCase(String color) {
        return facultyMapper.toRecordList(facultyRepository.getFacultiesByColorIgnoreCase(color));
    }

    public Collection<FacultyRecord> getFacultiesByNameIgnoreCase(String name) {
        return facultyMapper.toRecordList(facultyRepository.getFacultiesByNameIgnoreCase(name));
    }


    public Collection<FacultyRecord> findFacultyByColorIgnoreCaseOrNameIgnoreCase(String colorOrName) {
        return facultyMapper.toRecordList(facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(colorOrName));
    }


    public Collection<StudentRecord> findStudentByFaculty_id(long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + facultyId));
        return studentMapper.toRecordList(faculty.getStudents());
    }
}
