package ru.hogwarts.school.record;

import lombok.*;
import ru.hogwarts.school.entity.Faculty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.hogwarts.school.entity.Student} entity
 */

@AllArgsConstructor
@Data
@Getter
public class StudentRecord{
    private Long id;
    @NotNull(message = "Название имени должно быть заполнено")
    @NotBlank(message = "Название имени не должно быть пустым")
    private String name;
    @NotNull(message = "Возраст не может пустым")
    @Min(value = 17, message = "Возраст от 18")
    @Max(value = 40, message = "Возраст до 40")
    private int age;
    private Integer faculty_id;

    private Faculty faculty;

    public StudentRecord(){

    }

    public StudentRecord(long l, String test, int i, int i1) {
    }
}