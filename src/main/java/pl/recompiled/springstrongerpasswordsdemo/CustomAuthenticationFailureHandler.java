package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pl.recompiled.springstrongerpasswordsdemo.token.OneTimeTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final OneTimeTokenProvider oneTimeTokenProvider;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException ex) throws IOException {
        if (ex instanceof PasswordTooWeakException) {
            final String token = oneTimeTokenProvider.generate(((PasswordTooWeakException) ex).getUserId());
            resp.sendRedirect("/change-password?token=" + token);
        } else {
            resp.sendRedirect("/login?error");
        }
    }
}
