package crud.app.notesmanager.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
    String register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request, HttpServletResponse response);
}
