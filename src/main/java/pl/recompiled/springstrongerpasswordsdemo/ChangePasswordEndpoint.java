package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("change-password")
@RequiredArgsConstructor
public class ChangePasswordEndpoint {

    private final UserService userService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
    }
}
