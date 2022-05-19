package pl.recompiled.springstrongerpasswordsdemo.password;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.recompiled.springstrongerpasswordsdemo.UserService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Controller
@RequestMapping("change-password")
@RequiredArgsConstructor
public class ChangePasswordEndpoint {

    private final UserService userService;

    @GetMapping
    public String changePasswordView() {
        return "change-password";
    }

    @PostMapping
    @ResponseStatus(OK)
    public void changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
    }

    @ExceptionHandler(PasswordChangeTokenInvalidException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public void handlePasswordChangeTokenInvalidException() {
    }
}
