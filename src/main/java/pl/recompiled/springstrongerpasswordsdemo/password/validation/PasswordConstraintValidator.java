package pl.recompiled.springstrongerpasswordsdemo.password.validation;

import lombok.RequiredArgsConstructor;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist.FrequentPasswordListProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {

    private final PasswordValidatorService validatorService;
    private final FrequentPasswordListProvider passwordListProvider;

    @Override
    public void initialize(StrongPassword s) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        ValidationResult validationResult = validatorService.isValid(password);
        ValidationResult passwordListResult = passwordListProvider.validate(password);
        if (validationResult.isValid() && passwordListResult.isValid()) {
            return true;
        }
        final List<String> messages = new ArrayList<>();
        messages.addAll(validationResult.getMessages());
        messages.addAll(passwordListResult.getMessages());

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        String.join(";", messages))
                .addConstraintViolation();
        return false;
    }
}
