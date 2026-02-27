package crud.app.notesmanager.security.bruteforce;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final Cache<String, Integer> userAttempts =
            Caffeine.newBuilder()
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .build();
    private final Cache<String, Long> lockedUsers =
            Caffeine.newBuilder()
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .build();

    public int getAttempts(String ip){
        return userAttempts.get(ip, k->0);
    }

    public boolean isLocked(String ip){
        return lockedUsers.getIfPresent(ip)!=null;
    }

    public void addUserFailure(String ip){
        userAttempts.put(ip, this.getAttempts(ip) + 1);
        if (getAttempts(ip)>=3){
            lockedUsers.put(ip, System.currentTimeMillis());
        }
    }

    public void resetAttempts(String ip){
        userAttempts.invalidate(ip);
    }
}
