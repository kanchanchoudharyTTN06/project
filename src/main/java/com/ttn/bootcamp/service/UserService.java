package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;

import java.util.List;

public interface UserService {

    void accountActivationHandler(User user);

    void userAccountLockedEmailHandler(User user);

    List<UserDto> getAllUsers() throws GenericException;

    UserDto getUserById(long id) throws GenericException;

    String activateUserAccount(String token) throws GenericException;

    String activateUserAccountByAdmin(long id) throws GenericException;

    void checkForEmailExist(String email) throws GenericException;

    String forgotPassword(String email) throws GenericException;

    String resetPassword(ResetPassword resetPassword) throws GenericException;

    String updatePassword(ResetPassword resetPassword) throws GenericException;

    String reSendActivationLink(String email) throws GenericException;

    String deActivateUserAccountByAdmin(long id) throws GenericException;

    UserDto getUserByEmail(String email) throws GenericException;

    List<AddressDto> getAddressesByUserEmail(String email) throws GenericException;
}
