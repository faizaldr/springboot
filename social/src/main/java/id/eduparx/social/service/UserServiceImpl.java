package id.eduparx.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.model.User;
import id.eduparx.social.model.User.Role;
import id.eduparx.social.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    // DI
    @Autowired
    // mengurangi deklarasi dengan new
    // mengurangi pembuatan objek baru di memory
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
            u-> new UserDto(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getBio(),
                u.getRole()
            )
        ).toList();
    }

    @Override
    public UserDto createUser(String username,String email,String password,Role role){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(null);        
        user.setRole(role);
        User userResult= userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(userResult.getUsername());
        userDto.setEmail(userResult.getEmail());
        userDto.setRole(userResult.getRole());
        return userDto;
    }
}
