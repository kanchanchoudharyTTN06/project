package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface UserService {

    void accountActivationHandler(User user);

    List<UserDto> getAllUsers();

    UserDto getUserById(int id);

    String activateUserAccount(String token) throws GenericException;
}
