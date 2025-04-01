package sit.int202.springmodule2.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int202.springmodule2.config.FileStorageProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            if (!Files.exists(this.fileStorageLocation)) {
                Files.createDirectory(fileStorageLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while creating file dir.");
        }
    }

    public String store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Invalid file name format...");
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File Not Found: "  + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFile(String fileName) {
        try {
            Path filePath =  this.fileStorageLocation.resolve(fileName).normalize();

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new FileNotFoundException("File not found");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSupportContentType(MultipartFile file) {
        String contentType = file.getContentType();
        Set<String> allowFileTypes = Arrays
                .stream(fileStorageProperties.getAllowFileTypes())
                .collect(Collectors.toSet());

        return allowFileTypes.contains(contentType);
    }

    public String getMediaType(Resource resource) {
        try {
            return Files.probeContentType(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
