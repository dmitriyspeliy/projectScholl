package ru.hogwarts.school.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Avatar} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvatarRecord {
    private Long id;
    @NotNull(message = "Название файла должно быть заполнено")
    @NotBlank(message = "Название файла не должно быть пустым")
    private String filePath;
    @NotNull
    private long fileSize;
    @NotNull(message = "Тип файла должен быть заполнено")
    @NotBlank(message = "Тип файла не должен быть пустым")
    private String mediaType;
    private byte[] data;
    private Long studentId;
    private Student student;
}