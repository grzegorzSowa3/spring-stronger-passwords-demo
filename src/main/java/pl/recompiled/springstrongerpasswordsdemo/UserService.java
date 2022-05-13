package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.recompiled.springstrongerpasswordsdemo.token.OneTimeTokenProvider;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OneTimeTokenProvider ottProvider;

    public UserData createUser(CreateUserDto dto) {
        final User user = User.newInstance(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(user).toData();
    }

    public void changePassword(ChangePasswordDto dto) {
        final User user = ottProvider.use(dto.getToken())
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("Token invalid!"));
        userRepository.save(user.withPassword(passwordEncoder.encode(dto.getPassword())));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such username!"));
    }
}
