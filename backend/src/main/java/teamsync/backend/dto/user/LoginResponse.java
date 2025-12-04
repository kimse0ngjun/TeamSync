package teamsync.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String accesstoken;
    private String refreshtoken;
    private String email;
    private String name;
}
