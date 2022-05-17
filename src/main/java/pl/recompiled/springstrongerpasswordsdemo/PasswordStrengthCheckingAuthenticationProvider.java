package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class PasswordStrengthCheckingAuthenticationProvider extends DaoAuthenticationProvider {

    private final PasswordValidatorService validatorService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        ValidationResult validationResult = validatorService.isValid(authentication.getCredentials().toString());
        if (!validationResult.getRuleResult().isValid()) {
            throw new PasswordTooWeakException(((User) userDetails).getId());
        }
    }

//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        Authentication result = provider.authenticate(authentication);
//        ValidationResult validationResult = validatorService.isValid(authentication.getCredentials().toString());
//        if (!validationResult.getRuleResult().isValid()) {
//            System.out.println("dupa");
//            throw new PasswordTooWeakException(((User)result.getPrincipal()).getId());
//        }
//        System.out.println("sukces");
//        return result;
//    }

//    @Override
//    public boolean supports(Class<?> authentication) {
//        return provider.supports(authentication);
//    }
}
