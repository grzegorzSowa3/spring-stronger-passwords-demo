package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.PasswordValidatorService;
import pl.recompiled.springstrongerpasswordsdemo.token.OneTimeTokenProvider;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PasswordValidatorService passwordValidatorService;
    private final OneTimeTokenProvider oneTimeTokenProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordStrengthCheckingAuthenticationProvider authenticationProvider =
                new PasswordStrengthCheckingAuthenticationProvider(passwordValidatorService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        auth.authenticationProvider(authenticationProvider);

        // user with weak password, just for test
        // todo: remove
        userService.createUser(new CreateUserDto("user0", "weakPassword"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/change-password*").permitAll()
                .antMatchers("/password-lists*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler(oneTimeTokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, ex) -> resp.sendRedirect("/login"))
                .and()
                .logout()
                .and()
                .csrf().disable();
    }
}
