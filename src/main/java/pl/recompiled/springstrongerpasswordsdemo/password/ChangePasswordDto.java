package pl.recompiled.springstrongerpasswordsdemo.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.StrongPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    private String token;
    @StrongPassword
    private String password;
}
