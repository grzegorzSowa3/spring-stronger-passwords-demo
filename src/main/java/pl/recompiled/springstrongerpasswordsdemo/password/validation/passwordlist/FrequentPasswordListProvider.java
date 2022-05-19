package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import pl.recompiled.springstrongerpasswordsdemo.password.validation.ValidationResult;

public interface FrequentPasswordListProvider {
    ValidationResult validate(String password);
}
