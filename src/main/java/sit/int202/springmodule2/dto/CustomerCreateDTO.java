package sit.int202.springmodule2.dto;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerCreateDTO {

    @NotNull(message = "Please pass ID")
    private Integer id;

    @NotNull
    @NotBlank
    private String customerName;

    @NotNull
    @NotBlank
    private String contactLastName;

    @NotNull
    @NotBlank
    private String contactFirstName;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String addressLine1;

    @NotNull(message = "ไอเหี้ยตู่")
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String country;

    private MultipartFile file;



//    @AssertFalse(message = "Phone must be lower than 5")
//    private boolean isValidPhone() {
//        return phone.length() < 5;
//    }

}
