package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long id,
                                               @RequestParam MultipartFile avatar) {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is to big");
        }

        avatarService.uploadAvatar(id, avatar);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<byte[]> downloadPreviewAvatar(@PathVariable long id) {

        AvatarRecord avatarRecord = avatarService.findByStud(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatarRecord.getMediaType()));
        httpHeaders.setContentLength(avatarRecord.getData().length);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(avatarRecord.getData());
    }

    @GetMapping("/{id}/avatar")
    public void downloadAvatar(@PathVariable long id, HttpServletResponse response) {
        AvatarRecord avatarRecord = avatarService.findByStud(id);

        Path path = Path.of(avatarRecord.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatarRecord.getMediaType());
            response.setContentLength((int) avatarRecord.getFileSize());
            is.transferTo(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("getAllAvatarWithPaginator")
    public Collection<AvatarRecord> getAllAvatarWithPaginator(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        return avatarService.getAllAvatarWithPaginator(page,size);
    }

}
