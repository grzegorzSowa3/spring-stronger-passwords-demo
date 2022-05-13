package pl.recompiled.springstrongerpasswordsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    private String token;
    @StrongPassword
    private String password;
}
