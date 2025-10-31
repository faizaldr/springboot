package id.eduparx.social.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.model.User;
import id.eduparx.social.repository.UserRepository;

@Service
public class CacheUserService {

    private final UserRepository userRepository;

    public CacheUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Mengambil semua data user, dilakukan caching
    @Cacheable(value = "users", key = "'allUsers'")
    public List<UserDto> getAllUsers() {
        System.out.println(">> Mengambil user dari database");
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Menghapus cache "allUsers"
    @CacheEvict(value = "users", key = "'allUsers'")
    public void evictUsersCache() {
        System.out.println("Redis cache hapus allUsers");
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clearAllCache(){
        System.out.println("Semua cache terhapus");
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setRole(user.getRole());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        return dto;
    }

}
