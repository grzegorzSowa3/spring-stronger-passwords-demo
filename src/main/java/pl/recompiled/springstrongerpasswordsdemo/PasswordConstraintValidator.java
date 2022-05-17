package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {

    private final PasswordValidatorService validatorService;

    @Override
    public void initialize(StrongPassword s) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        ValidationResult result = validatorService.isValid(password);
        if (result.getRuleResult().isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        String.join(";", result.getMessages()))
                .addConstraintViolation();
        return false;
    }
}
