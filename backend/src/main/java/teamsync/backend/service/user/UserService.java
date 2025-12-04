package teamsync.backend.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamsync.backend.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
