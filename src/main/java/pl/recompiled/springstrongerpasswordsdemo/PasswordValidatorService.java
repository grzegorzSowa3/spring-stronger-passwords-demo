package pl.recompiled.springstrongerpasswordsdemo;

import lombok.Data;
import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class PasswordValidatorService {

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
        return new ValidationResult(ruleResult, validator.getMessages(ruleResult));
    }
}

@Data
class ValidationResult {
    private final RuleResult ruleResult;
    private final List<String> messages;
}
