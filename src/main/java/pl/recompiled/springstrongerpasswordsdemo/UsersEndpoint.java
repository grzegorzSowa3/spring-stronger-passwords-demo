package pl.recompiled.springstrongerpasswordsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersEndpoint {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserData> createUser(@RequestBody @Valid CreateUserDto dto) {
        final UserData user = userService.createUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }
}
