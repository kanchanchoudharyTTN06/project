package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface UserService {

    void accountActivationHandler(User user);

    List<UserDto> getAllUsers();

    UserDto getUserById(long id);

    String activateUserAccount(String token) throws GenericException;

    String activateUserAccountByAdmin(long id) throws GenericException;

    void checkForEmailExist(String email) throws GenericException;

    String forgotPassword(String email) throws GenericException;

    String resetPassword(ResetPassword resetPassword) throws GenericException;

    String reSendActivationLink(String email) throws GenericException;
}
