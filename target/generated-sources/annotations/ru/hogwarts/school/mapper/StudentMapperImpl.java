package ru.hogwarts.school.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.StudentRecord;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T16:01:08+0700",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentRecord studentRecord) {
        if ( studentRecord == null ) {
            return null;
        }

        Student student = new Student();

        student.setName( studentRecord.getName() );
        student.setAge( studentRecord.getAge() );
        student.setFaculty_id( studentRecord.getFaculty_id() );
        student.setFaculty( studentRecord.getFaculty() );

        return student;
    }

    @Override
    public StudentRecord toRecord(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentRecord studentRecord = new StudentRecord();

        studentRecord.setId( student.getId() );
        studentRecord.setName( student.getName() );
        studentRecord.setAge( student.getAge() );
        studentRecord.setFaculty_id( student.getFaculty_id() );
        studentRecord.setFaculty( student.getFaculty() );

        return studentRecord;
    }

    @Override
    public Collection<Student> toEntityList(Collection<StudentRecord> studentRecord) {
        if ( studentRecord == null ) {
            return null;
        }

        Collection<Student> collection = new ArrayList<Student>( studentRecord.size() );
        for ( StudentRecord studentRecord1 : studentRecord ) {
            collection.add( toEntity( studentRecord1 ) );
        }

        return collection;
    }

    @Override
    public Collection<StudentRecord> toRecordList(Collection<Student> student) {
        if ( student == null ) {
            return null;
        }

        Collection<StudentRecord> collection = new ArrayList<StudentRecord>( student.size() );
        for ( Student student1 : student ) {
            collection.add( toRecord( student1 ) );
        }

        return collection;
    }
}
