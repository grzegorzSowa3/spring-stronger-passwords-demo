package pl.recompiled.springstrongerpasswordsdemo.password.validation;

import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordValidatorService {

    public ValidationResult isValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 1024),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
                new WhitespaceRule()));

        RuleResult ruleResult = validator.validate(new PasswordData(password));
        return new ValidationResult(ruleResult.isValid(), validator.getMessages(ruleResult));
    }
}
