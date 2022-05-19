package pl.recompiled.springstrongerpasswordsdemo.password.validation;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResult {
    private final boolean valid;
    private final List<String> messages;
}