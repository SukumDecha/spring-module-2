package sit.int202.springmodule2.controllers;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.springmodule2.config.FileStorageProperties;
import sit.int202.springmodule2.dto.CustomerCreateDTO;
import sit.int202.springmodule2.services.FileService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/uploads")
public class FileController {

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private FileService service;


    @GetMapping("/dir")
    public ResponseEntity<String> getUploadPath() {
        return ResponseEntity.ok(fileStorageProperties.getUploadDir());
    }

    @PostMapping("")
    public ResponseEntity<String>
                uploadFile(@Valid @ModelAttribute CustomerCreateDTO payload) {

        if(!service.isSupportContentType(payload.getFile())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Does not support file type: " + payload.getFile().getContentType());
        }

        String fileName = service.store(payload.getFile());

        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource resource = service.loadFileAsResource(fileName);

        String mediaType = service.getMediaType(resource);

        return ResponseEntity.ok().contentType(MediaType.valueOf(mediaType)).body(resource);
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity deleteFile(@PathVariable String fileName) {
        service.removeFile(fileName);

        return ResponseEntity.ok(fileName + " has been removed");
    }

}