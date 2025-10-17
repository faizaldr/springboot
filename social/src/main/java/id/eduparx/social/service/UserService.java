package id.eduparx.social.service;

import java.util.List;

import id.eduparx.social.dto.UserDto;

public interface UserService {
    List<UserDto> getAll();
}