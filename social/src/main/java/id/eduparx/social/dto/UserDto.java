package id.eduparx.social.dto;

import id.eduparx.social.model.User.Role;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private Role role = Role.USER;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String bio, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
