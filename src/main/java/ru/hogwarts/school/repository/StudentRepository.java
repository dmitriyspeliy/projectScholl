package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.StudentRecord;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> getStudentsByAge(Integer age);

    Collection<Student> findStudentByAgeBetween(Integer from,Integer to);

    @Query(value = "select count(*)\n" +
            "from students",nativeQuery = true)
    Integer findCountOfStudents();

    @Query(value = "select avg(student_age)\n" +
            "from students",nativeQuery = true)
    Integer averageOfAge();

    @Query(value = " select *\n" +
            "    from students\n" +
            "    order by student_id desc\n" +
            "    limit 5",nativeQuery = true)
    Collection<Student> fiveLastOfStudentsByID();


}