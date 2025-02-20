package hexlet.code.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String password;

}
