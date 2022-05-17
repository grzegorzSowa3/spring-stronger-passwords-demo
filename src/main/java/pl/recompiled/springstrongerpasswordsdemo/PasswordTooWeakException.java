package pl.recompiled.springstrongerpasswordsdemo;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

import java.util.UUID;

@Getter
public class PasswordTooWeakException extends AuthenticationException {

    private final UUID userId;

    public PasswordTooWeakException(UUID userId, Throwable cause) {
        super("Password too weak", cause);
        this.userId = userId;
    }

    public PasswordTooWeakException(UUID userId) {
        super("Password too weak");
        this.userId = userId;
    }
}
