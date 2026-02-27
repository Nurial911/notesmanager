package crud.app.notesmanager.security.bruteforce;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object details = event.getAuthentication().getDetails();
        String ip = null;
        if (details instanceof WebAuthenticationDetails webAuthenticationDetails) {
            ip = webAuthenticationDetails.getRemoteAddress();
        }
        ip = ip != null ? ip : "unknown";
        loginAttemptService.addUserFailure(ip);
    }
}
