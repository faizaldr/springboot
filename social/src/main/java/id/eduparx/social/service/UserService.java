package id.eduparx.social.service;

import java.util.List;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.model.User.Role;

public interface UserService {
    List<UserDto> getAll();

    UserDto createUser(String username,String email,String password,Role role);

    UserDto updateUser(Long id, String username,String email,String password,Role role);

    UserDto deleteUser(Long id);
}