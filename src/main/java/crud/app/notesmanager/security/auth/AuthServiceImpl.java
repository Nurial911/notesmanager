package crud.app.notesmanager.security.auth;

import crud.app.notesmanager.security.jwt.JwtService;
import crud.app.notesmanager.security.user.Role;
import crud.app.notesmanager.security.user.User;
import crud.app.notesmanager.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(
            @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword());

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password")
                );

        var token = jwtService.generateToken(user);

        return token.toString();
    }

    @Override
    public String register(
            @RequestBody RegisterRequest registerRequest, HttpServletRequest request, HttpServletResponse response) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        var token = jwtService.generateToken(savedUser);

        return token.toString();
    }
}
