package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.AddressRepository;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.repository.TokenRepository;
import com.ttn.bootcamp.repository.UserRepository;
import com.ttn.bootcamp.service.EmailService;
import com.ttn.bootcamp.service.UserService;
import com.ttn.bootcamp.token.AccountActivationToken;
import com.ttn.bootcamp.token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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
        AccountActivationToken accountActivationToken = new AccountActivationToken(user.getId());

        String body = "<html>\n" +
                "    <body>To activate your account, please <a href='http://localhost:8080/users/activate/account?token=" + accountActivationToken.getToken() + "'>click here</a></body>\n" +
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
    public UserDto getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(User::toUserDto).orElse(null);
    }

    @Override
    public String activateUserAccount(String token) throws GenericException {
        //AuthToken authToken = tokenRepository.findByToken(token);
        AccountActivationToken accountActivationToken =
                AccountActivationToken.getAccountActivationTokenBean(token);
        boolean isValid = accountActivationToken.isValidToken(180);

        if (!isValid)
            throw new GenericException("Invalid token", HttpStatus.BAD_REQUEST);

        User user = userRepository.getById(accountActivationToken.getUserId());
        user.setActive(true);
        userRepository.save(user);
        accountActivationConfirmationHandler(user);
        return "Congratulations! " + user.getFirstName() + ", your account is activated.";
    }

    private void accountActivationConfirmationHandler(User user) {
        String body = "<html>\n" +
                "    <body>Dear " + user.getFirstName() + ",<br><br>Your account has been activated successfully.</body>\n" +
                "</html>";
        String subject = "Congratulations!";
        emailService.sendEmail(user.getEmail(), subject, body);
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

    /*public String forgotPassword(String email) throws GenericException {
        User user = userRepository.findByEmail(email);
        if(Objects.isNull(user))
            throw new GenericException("Email id not found in database.", HttpStatus.BAD_REQUEST);


    }*/
}
