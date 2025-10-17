package id.eduparx.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.model.User;
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

}
