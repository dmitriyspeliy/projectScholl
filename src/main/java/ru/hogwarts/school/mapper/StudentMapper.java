package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.StudentRecord;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRecord studentRecord);

    StudentRecord toRecord(Student student);

    Collection<Student> toEntityList(Collection<StudentRecord> studentRecord);

    Collection<StudentRecord> toRecordList(Collection<Student> student);
}
