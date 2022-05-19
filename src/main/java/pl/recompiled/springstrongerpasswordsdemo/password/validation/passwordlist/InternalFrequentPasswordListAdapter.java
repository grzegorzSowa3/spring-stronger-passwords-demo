package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.PasswordValidatorService;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class InternalFrequentPasswordListAdapter implements FrequentPasswordListProvider {

    private final PasswordValidatorService validatorService;
    private final FrequentPasswordRepository repository;

    Boolean addPassword(String password) {
        final ValidationResult validationResult = validatorService.isValid(password);
        if (validationResult.isValid() && !repository.existsById(password)) {
            repository.save(FrequentPassword.of(password));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ValidationResult validate(String password) {
        final boolean exists = repository.existsById(password);
        final List<String> messages = new ArrayList<>();
        if (exists) {
            messages.add("Password is a common password found in data breaches!");
        }
        return new ValidationResult(!exists, messages);
    }
}
