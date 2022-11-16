package ru.hogwarts.school.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Setter
@Getter
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", nullable = false)
    private Long id;
    @Column(name = "avatar_filepath", nullable = false)
    private String filePath;
    @Column(name = "avatar_filesize", nullable = false)
    private long fileSize;
    @Column(name = "avatar_mediatype", nullable = false)
    private String mediaType;
    @Lob
    @Column(name = "avatar_data", nullable = false)
    private byte[] data;

    @Column(name = "studentid", nullable = false)
    private Long studentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "studentid",insertable = false, updatable = false)
    private Student student;

    public Avatar() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize && Objects.equals(id, avatar.id) && Objects.equals(filePath, avatar.filePath) && Objects.equals(mediaType, avatar.mediaType) && Arrays.equals(data, avatar.data) && Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, student);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
