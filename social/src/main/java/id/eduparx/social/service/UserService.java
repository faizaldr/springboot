package id.eduparx.social.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.dto.UserProfileUpdateDto;
import id.eduparx.social.model.User;
import id.eduparx.social.model.User.Role;

public interface UserService {
    List<UserDto> getAll();

    UserDto createUser(String username,String email,String password,Role role);

    UserDto updateUser(Long id, String username,String email,String password,Role role);

    UserDto deleteUser(Long id);

    User getUserById(Long userId);

    // update nama lengkap, bio, foto profile
    UserDto updateProfile(Long userId, UserProfileUpdateDto updateDto);

    // foto profile
    UserDto updateProfileImage(Long userId, String imageUrl);

    // Mengambil semua user dengan pagination, pencarian, dan filter role
    Page<UserDto> getAllUsers(String search, Role role, Pageable pageable);

}