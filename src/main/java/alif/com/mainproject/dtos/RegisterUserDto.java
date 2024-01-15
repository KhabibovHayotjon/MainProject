package alif.com.mainproject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserDto {



    @NotBlank(message = "fullName cannot be empty")
    private String name;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 6, message = "password is can be minimum 6 characters")
    private String password;

    @Pattern(regexp = "^[0-9]{12}$", message = "phone is incorrect")
    @NotBlank(message = "phone cannot be empty")
    private String phoneNumber;

}
