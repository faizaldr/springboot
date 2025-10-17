package id.eduparx.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.model.User;
import id.eduparx.social.service.UserServiceImpl;

// PASSWORD

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getMethodName() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody String username, @RequestBody String email,
            @RequestBody String password, @RequestBody User.Role role) {
        UserDto userDto = userService.createUser(username, email, password, role);
        return ResponseEntity.ok(userDto);
    }

}
