package id.eduparx.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.dto.UserProfileUpdateDto;
import id.eduparx.social.exception.ResourceNotFoundException;
import id.eduparx.social.model.User;
import id.eduparx.social.model.User.Role;
import id.eduparx.social.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final FileStorageService fileStorageService;
    // DI
    @Autowired
    // mengurangi deklarasi dengan new
    // mengurangi pembuatan objek baru di memory
    private UserRepository userRepository;

    UserServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                u -> new UserDto(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getBio(),
                        u.getRole()))
                .toList();
    }

    @Override
    public UserDto createUser(String username, String email, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        User userResult = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(userResult.getUsername());
        userDto.setEmail(userResult.getEmail());
        userDto.setRole(userResult.getRole());
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, String username, String email, String password, Role role) {
        User userResult = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Tidak Ditemukan"));

        userResult.setUsername(username);
        userResult.setEmail(email);
        userResult.setPassword(password);
        userResult.setRole(role);
        userRepository.save(userResult);

        UserDto userDto = new UserDto();
        userDto.setUsername(userResult.getUsername());
        userDto.setEmail(userResult.getEmail());
        userDto.setRole(userResult.getRole());
        return userDto;
    }

    @Override
    public UserDto deleteUser(Long id) {
        User userResult = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Tidak Ditemukan"));

        userRepository.delete(userResult);

        UserDto userDto = new UserDto();
        userDto.setUsername(userResult.getUsername());
        userDto.setEmail(userResult.getEmail());
        userDto.setRole(userResult.getRole());
        return userDto;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan id :" + userId));
    }

    @Override
    public Page<UserDto> getAllUsers(String search, Role role, Pageable pageable) {
        Page<User> users;

        if (search != null && !search.isEmpty() && role != null) {
            users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndRole(
                    search, search, role, pageable);
        } else if (search != null & !search.isEmpty()) {
            users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search,
                    pageable);
        } else if (role != null) {
            users = userRepository.findByRole(role, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(UserDto::fromModel);
    }

    @Transactional
    public UserDto updateProfile(Long userId, UserProfileUpdateDto updateDto) {
        User user = getUserById(userId);

        if (updateDto.getFullName() != null) {
            user.setFullName(updateDto.getFullName());
        } else if (updateDto.getBio() != null) {
            user.setBio(updateDto.getBio());
        } else if (updateDto.getProfileImageUrl() != null) {
            if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
                String oldFileName = extractFileNameFromUrl(user.getProfileImageUrl());
                fileStorageService.deleteFile(oldFileName);
            }
            user.setProfileImageUrl(updateDto.getProfileImageUrl());

        }

        User updatedUser = userRepository.save(user);
        return UserDto.fromModel(updatedUser);
    }

    @Transactional
    public UserDto updateProfileImage(Long userId, String imageUrl) {
        User user = getUserById(userId);

        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            String oldFileName = extractFileNameFromUrl(user.getProfileImageUrl());
            fileStorageService.deleteFile(oldFileName);
        }

        user.setProfileImageUrl(imageUrl);
        User updatedUser = userRepository.save(user);
        return UserDto.fromModel(updatedUser);
    }

    private String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        int lastSlash = url.lastIndexOf("/");
        return lastSlash >= 0 ? url.substring(lastSlash + 1) : url;
    }
}
