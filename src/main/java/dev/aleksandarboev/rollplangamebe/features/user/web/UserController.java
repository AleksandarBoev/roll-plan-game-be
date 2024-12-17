package dev.aleksandarboev.rollplangamebe.features.user.web;

import dev.aleksandarboev.rollplangamebe.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
