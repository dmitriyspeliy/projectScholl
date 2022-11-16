package ru.hogwarts.school.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.entity.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link ru.hogwarts.school.entity.Faculty} entity
 */

@AllArgsConstructor
@Data
public class FacultyRecord {
    private Long id;
    @NotNull(message = "Название имени должно быть заполнено")
    @NotBlank(message = "Название имени не должно быть пустым")
    private String name;
    @NotNull(message = "Название цвета должно быть заполнено")
    @NotBlank(message = "Название цвета не должно быть пустым")
    private String color;

    private Set<Student> students;

    public FacultyRecord() {
    }

    public FacultyRecord(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}