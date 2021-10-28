package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto) throws GenericException;

    List<UserDto> getAllUsers();

    UserDto getUserById(int id);

    String activateUserAccount(String token) throws GenericException;
}
