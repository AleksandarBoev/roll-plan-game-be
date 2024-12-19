package dev.aleksandarboev.rollplangamebe.features.user.web;

import dev.aleksandarboev.rollplangamebe.features.user.UserService;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserLoginRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserLoginResponse;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserRegistrationRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse responseBody = userService.registerUser(userRegistrationRequest);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        Optional<UserLoginResponse> responseBody = userService.loginUser(userLoginRequest);

        return responseBody
                .map(userLoginResponse -> new ResponseEntity<>(userLoginResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
