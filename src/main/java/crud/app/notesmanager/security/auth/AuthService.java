package crud.app.notesmanager.security.auth;

import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    String login(@RequestBody LoginRequest loginRequest);
}
