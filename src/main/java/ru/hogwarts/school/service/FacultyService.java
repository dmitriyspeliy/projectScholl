package ru.hogwarts.school.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FacultyService.class);


    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
        this.studentMapper = studentMapper;
    }

    public FacultyRecord createFaculty(FacultyRecord facultyRecord) {
        logger.info("Was invoked method for create new Faculty");
        logger.debug("Check before create new Faculty = {}", facultyRecord);
        return facultyMapper.toRecord(facultyRepository.save(facultyMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord findFacultyByID(long id) {
        return facultyMapper.toRecord(getByID(id));
    }

    public FacultyRecord updateFaculty(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = getByID(id);
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        logger.info("Was invoked method for updateFaculty");
        logger.debug("check before save {}", oldFaculty);
        return facultyMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord deleteFacultyByID(long id) {
        Faculty faculty = getByID(id);
        logger.info("Was invoked method for delete faculty from db");
        facultyRepository.delete(faculty);
        return facultyMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> getAllFaculties() {
        logger.info("Was invoked method for get all faculty from db");
        return facultyMapper.toRecordList(facultyRepository.findAll());
    }

    public Collection<FacultyRecord> getFacultiesByColorIgnoreCase(String color) {
        logger.info("Was invoked method for get all faculty by color");
        return facultyMapper.toRecordList(facultyRepository.getFacultiesByColorIgnoreCase(color));
    }

    public Collection<FacultyRecord> getFacultiesByNameIgnoreCase(String name) {
        logger.info("Was invoked method for get all faculty by name");
        return facultyMapper.toRecordList(facultyRepository.getFacultiesByNameIgnoreCase(name));
    }


    public Collection<FacultyRecord> findFacultyByColorIgnoreCaseOrNameIgnoreCase(String colorOrName) {
        logger.info("Was invoked method for get all faculty by color and name");
        return facultyMapper.toRecordList(facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(colorOrName));
    }


    public Collection<StudentRecord> findStudentByFaculty_id(long facultyId) {
        Faculty faculty = getByID(facultyId);
        logger.info("Was invoked method for get all student with faculty by id = {}", facultyId);
        return studentMapper.toRecordList(faculty.getStudents());
    }

    private Faculty getByID(long id) {
        logger.info("Was invoked method for find by ID = {}", id);
        logger.error("Elem with id = {}, not found. An exception occurred!", id);
        return facultyRepository.findById(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id));
    }
}
