package sit.int202.springmodule2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageProperties {

    private String uploadDir;
    private String[] allowFileTypes;

}
