package alif.com.mainproject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class UsersDto {

    @NotBlank(message = "fullName cannot be empty")
    @Size(min = 3,max = 100, message = "fullname between 3 and 100 letters")
    private String name;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 6, message = "password is can be minimum 6 characters")
    private String password;

    @NotBlank(message = "phone cannot be empty")
    @Pattern(regexp = "^[0-9]{12}$", message = "phone is incorrect")
    private String phoneNumber;

    @NotNull(message = "role id nannot be empty")
    private Long roleId;

}
