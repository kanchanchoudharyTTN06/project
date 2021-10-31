package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.enums.UserRole;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.AddressRepository;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.repository.TokenRepository;
import com.ttn.bootcamp.repository.UserRepository;
import com.ttn.bootcamp.service.EmailService;
import com.ttn.bootcamp.service.UserService;
import com.ttn.bootcamp.token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void accountActivationHandler(User user) {
        AuthToken authToken = createAuthToken(user);

        String body = "<html>\n" +
                "    <body>To activate your account, please <a href='http://localhost:8080/users/activate/account?token=" + authToken.getToken() + "'>click here</a></body>\n" +
                "</html>";
        String subject = "Hey " + user.getFirstName() + "! Activate your account.";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(user.toUserDto());
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(User::toUserDto).orElse(null);
    }

    @Override
    public String activateUserAccount(String token) throws GenericException {
        AuthToken authToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authToken))
            throw new GenericException("Invalid token", HttpStatus.BAD_REQUEST);

        final Calendar cal = Calendar.getInstance();
        if ((authToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            throw new GenericException("Sorry! Token has expired.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = authToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        return "Congratulations! " + user.getFirstName() + ", your account is activated.";
    }

    private AuthToken createAuthToken(User user) {
        AuthToken authToken = new AuthToken(user);
        return tokenRepository.save(authToken);
    }

    @Override
    public void checkForEmailExist(String email) throws GenericException {
        if (Objects.nonNull(userRepository.findByEmail(email)))
            throw new GenericException("Email id already registered.", HttpStatus.BAD_REQUEST);
    }
}
