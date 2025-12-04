package teamsync.backend.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamsync.backend.dto.user.LoginRequest;
import teamsync.backend.dto.user.LoginResponse;
import teamsync.backend.dto.user.SignupRequest;
import teamsync.backend.dto.user.SignupResponse;
import teamsync.backend.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    // 회원가입
    @PostMapping("/signup")
    public SignupResponse signup(@RequestBody SignupRequest req) {
        return authService.signup(req);
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
