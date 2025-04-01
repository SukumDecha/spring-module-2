package sit.int202.springmodule2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import sit.int202.springmodule2.config.FileStorageProperties;

@SpringBootApplication
public class SpringModule2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringModule2Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
