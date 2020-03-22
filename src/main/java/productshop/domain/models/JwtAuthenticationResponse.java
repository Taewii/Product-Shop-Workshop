package productshop.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthenticationResponse {

    private String id;
    private String username;
    private String token;
    private List<String> roles = new ArrayList<>();
}
