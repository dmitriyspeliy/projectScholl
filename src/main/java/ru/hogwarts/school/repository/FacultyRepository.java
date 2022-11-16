package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> getFacultiesByColorIgnoreCase(String color);

    Collection<Faculty> getFacultiesByNameIgnoreCase(String name);

    @Query("select f from Faculty f where upper(f.color) = upper(:nameOrColor) or upper(f.name) = upper(:nameOrColor)")
    Collection<Faculty> findFacultiesByColorIgnoreCaseOrNameIgnoreCase(@Param("nameOrColor") String nameOrColor);

}