package id.eduparx.social.service;

import id.eduparx.social.config.security.JwtTokenProvider;
import id.eduparx.social.dto.AuthRequest;
import id.eduparx.social.dto.AuthResponse;
import id.eduparx.social.dto.UserDtoRequest;
import id.eduparx.social.model.User;
import id.eduparx.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service untuk Authentication dan User Registration
 */
@Service
@Transactional
public class MyAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * User Login
     */
    public AuthResponse login(AuthRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsernameOrEmail(),
                request.getPassword()
            )
        );

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Get user details
        User user = userRepository.findByUsernameOrEmail(
            request.getUsernameOrEmail(), 
            request.getUsernameOrEmail()
        ).orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(jwt, user.getId(), user.getUsername(), 
                               user.getEmail(), /*user.getFullName(),*/ user.getRole());
    }

    /**
     * User Registration
     */
    public AuthResponse register(UserDtoRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username sudah digunakan!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // user.setFullName(request.getFullName());
        user.setBio(request.getBio());
        user.setRole(User.Role.USER); // Default role
        // user.setIsActive(true);

        // Save user to database
        user = userRepository.save(user);

        // Generate JWT token for auto-login after registration
        String jwt = tokenProvider.generateTokenFromUserId(user.getId());

        return new AuthResponse(jwt, user.getId(), user.getUsername(), 
                               user.getEmail()/*, user.getFullName() */, user.getRole());
    }

    /**
     * Validate if username is available
     */
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    /**
     * Validate if email is available
     */
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    /**
     * Change Password
     */
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Password lama tidak cocok");
        }

        // Update with new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Reset Password (simplified - in real app would need email verification)
     */
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email tidak ditemukan"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}