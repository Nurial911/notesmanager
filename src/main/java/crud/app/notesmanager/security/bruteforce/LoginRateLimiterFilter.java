package crud.app.notesmanager.security.bruteforce;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class LoginRateLimiterFilter extends OncePerRequestFilter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final LoginAttemptService loginAttemptService;

    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !matcher.match("/auth/login",request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        ip = ip != null ? ip : "unknown";
        if (loginAttemptService.isLocked(ip)){
            response.setStatus(429);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
