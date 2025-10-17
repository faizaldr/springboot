package id.eduparx.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.dto.UserDtoRequest;
import id.eduparx.social.model.User;
import id.eduparx.social.service.UserServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDtoRequest body) {
        UserDto userDto = userService.createUser(body.getUsername(), body.getEmail(), body.getPassword(),
                body.getRole());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDtoRequest body) {
        UserDto userDto = userService.updateUser(id, body.getUsername(), body.getEmail(), body.getPassword(),
                body.getRole());
        return userDto;
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id){
        UserDto userDto = userService.deleteUser(id);
        return userDto;
    }
}
