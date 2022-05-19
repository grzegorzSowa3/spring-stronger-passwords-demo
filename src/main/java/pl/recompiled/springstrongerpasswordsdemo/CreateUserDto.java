package pl.recompiled.springstrongerpasswordsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.recompiled.springstrongerpasswordsdemo.password.validation.StrongPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    @StrongPassword
    private String password;
}
