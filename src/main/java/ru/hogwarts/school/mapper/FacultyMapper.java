package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.record.FacultyRecord;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    @Mapping(target = "id", ignore = true)
    Faculty toEntity(FacultyRecord facultyRecord);

    FacultyRecord toRecord(Faculty faculty);

    Collection<Faculty> toEntityList(Collection<FacultyRecord> facultyRecords);

    Collection<FacultyRecord> toRecordList(Collection<Faculty> faculty);
}
