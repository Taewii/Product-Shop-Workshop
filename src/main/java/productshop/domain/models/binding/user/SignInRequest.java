package productshop.domain.models.binding.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static productshop.config.Constants.BLANK_PASSWORD_MESSAGE;
import static productshop.config.Constants.BLANK_USERNAME_MESSAGE;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequest {

    @NotBlank(message = BLANK_USERNAME_MESSAGE)
    private String usernameOrEmail;

    @NotBlank(message = BLANK_PASSWORD_MESSAGE)
    private String password;
}
