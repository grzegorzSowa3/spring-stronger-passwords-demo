package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.PasswordValidatorService;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.ValidationResult;

@RequiredArgsConstructor
public class PasswordStrengthCheckingAuthenticationProvider extends DaoAuthenticationProvider {

    private final PasswordValidatorService validatorService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        ValidationResult validationResult = validatorService.isValid(authentication.getCredentials().toString());
        if (!validationResult.isValid()) {
            throw new PasswordTooWeakException(((User) userDetails).getId());
        }
    }
}
