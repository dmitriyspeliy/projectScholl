package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.exception.ElemNotFound;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${avatar.cover.dir.path}")
    private String avatarDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final AvatarMapper avatarMapper;


    public AvatarService(AvatarRepository avatarRepository, StudentService studentService, StudentMapper studentMapper, AvatarMapper avatarMapper) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.avatarMapper = avatarMapper;
    }

    public void uploadAvatar(long id, MultipartFile file) {
        StudentRecord studentRecord = studentService.findStudentByID(id);

        Path filePath = Path.of(avatarDir
                , id + "." +
                        getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AvatarRecord avatarRecord = findById(id);
        avatarRecord.setStudent(studentMapper.toEntity(studentRecord));
        avatarRecord.setFilePath(filePath.toString());
        avatarRecord.setFileSize(file.getSize());
        avatarRecord.setMediaType(file.getContentType());
        avatarRecord.setData(generateImagePreview(filePath));
        avatarRecord.setStudentId(id);

        avatarRepository.save(avatarMapper.toEntity(avatarRecord));


    }

    public AvatarRecord findById(long id) {
        return avatarMapper.toRecord(avatarRepository.findById(id).orElse(new Avatar()));
    }

    public AvatarRecord findByStud(long id) {
        return avatarMapper.toRecord(avatarRepository.findByStudentId(id).orElseThrow(() -> new ElemNotFound("Нет элемента с id " + id)));
    }

    private byte[] generateImagePreview(Path filePath) {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);

            return baos.toByteArray();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Collection<AvatarRecord> getAllAvatarWithPaginator(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return avatarMapper.toRecordList(avatarRepository.findAll(pageRequest).getContent());
    }
}
