package ru.hogwarts.school.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T16:01:08+0700",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class FacultyMapperImpl implements FacultyMapper {

    @Override
    public Faculty toEntity(FacultyRecord facultyRecord) {
        if ( facultyRecord == null ) {
            return null;
        }

        Faculty faculty = new Faculty();

        faculty.setName( facultyRecord.getName() );
        faculty.setColor( facultyRecord.getColor() );
        Set<Student> set = facultyRecord.getStudents();
        if ( set != null ) {
            faculty.setStudents( new HashSet<Student>( set ) );
        }

        return faculty;
    }

    @Override
    public FacultyRecord toRecord(Faculty faculty) {
        if ( faculty == null ) {
            return null;
        }

        FacultyRecord facultyRecord = new FacultyRecord();

        facultyRecord.setId( faculty.getId() );
        facultyRecord.setName( faculty.getName() );
        facultyRecord.setColor( faculty.getColor() );
        Set<Student> set = faculty.getStudents();
        if ( set != null ) {
            facultyRecord.setStudents( new HashSet<Student>( set ) );
        }

        return facultyRecord;
    }

    @Override
    public Collection<Faculty> toEntityList(Collection<FacultyRecord> facultyRecords) {
        if ( facultyRecords == null ) {
            return null;
        }

        Collection<Faculty> collection = new ArrayList<Faculty>( facultyRecords.size() );
        for ( FacultyRecord facultyRecord : facultyRecords ) {
            collection.add( toEntity( facultyRecord ) );
        }

        return collection;
    }

    @Override
    public Collection<FacultyRecord> toRecordList(Collection<Faculty> faculty) {
        if ( faculty == null ) {
            return null;
        }

        Collection<FacultyRecord> collection = new ArrayList<FacultyRecord>( faculty.size() );
        for ( Faculty faculty1 : faculty ) {
            collection.add( toRecord( faculty1 ) );
        }

        return collection;
    }
}
