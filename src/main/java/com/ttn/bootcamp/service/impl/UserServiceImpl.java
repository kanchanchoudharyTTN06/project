package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.ApplicationConstants;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.repository.AddressRepository;
import com.ttn.bootcamp.util.Utility;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.TokenRepository;
import com.ttn.bootcamp.repository.UserRepository;
import com.ttn.bootcamp.service.EmailService;
import com.ttn.bootcamp.service.UserService;
import com.ttn.bootcamp.token.AccountActivationToken;
import com.ttn.bootcamp.token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EmailService emailService;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService, TokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    @Override
    public void accountActivationHandler(User user) {
        AccountActivationToken accountActivationToken = new AccountActivationToken(user.getId());

        String body = "<html>\n" +
                "    <body>To activate your account, please <a href='http://localhost:8080/users/activate/account?token=" + accountActivationToken.getToken() + "'>click here</a><br>" +
                "This link is valid for 3 hours.</body>\n" +
                "</html>";
        String subject = "Hey " + user.getFirstName() + "! Activate your account.";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public void userAccountLockedEmailHandler(User user) {
        String body = "<html>\n" +
                "    <body>Dear " + user.getFirstName() + ",<br><br>Your account has locked because of 3 successive invalid attempt.</body>\n" +
                "</html>";
        String subject = "Your Account has Locked!";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public List<UserDto> getAllUsers() throws GenericException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(user.toUserDto());
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(long id) throws GenericException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent())
            return userOptional.get().toUserDto();
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public String activateUserAccount(String token) throws GenericException {
        AccountActivationToken accountActivationToken =
                AccountActivationToken.getAccountActivationTokenBean(token);

        checkForTokenValidityAndExpiry(accountActivationToken, 180);

        Optional<User> user = userRepository.findById(accountActivationToken.getUserId());
        if (user.isPresent()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            accountActivationConfirmationHandler(user.get());
            return "Congratulations! " + user.get().getFirstName() + ", your account is activated.";
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public String activateUserAccountByAdmin(long id) throws GenericException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().isActive())
                return ApplicationConstants.SUCCESS_RESPONSE;
            user.get().setActive(true);
            userRepository.save(user.get());
            accountActivationConfirmationHandler(user.get());
            return ApplicationConstants.SUCCESS_RESPONSE;
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    public String deActivateUserAccountByAdmin(long id) throws GenericException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (!user.get().isActive())
                return ApplicationConstants.SUCCESS_RESPONSE;
            user.get().setActive(false);
            userRepository.save(user.get());
            accountDeActivationConfirmationHandler(user.get());
            return ApplicationConstants.SUCCESS_RESPONSE;
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    private void accountDeActivationConfirmationHandler(User user) {
        String body = "<html>\n" +
                "    <body>Dear " + user.getFirstName() + ",<br><br>Your account has been de-activated.</body>\n" +
                "</html>";
        String subject = "Account Deactivated!";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    private void checkForTokenValidityAndExpiry(AccountActivationToken accountActivationToken, int expiryMinute) throws GenericException {
        if (Objects.isNull(accountActivationToken) ||
                !ApplicationConstants.SECRETE_CODE.equals(accountActivationToken.getSecreteCode()))
            throw new GenericException("Invalid token", HttpStatus.BAD_REQUEST);

        boolean isNotExpired = accountActivationToken.isNotExpired(expiryMinute);

        if (!isNotExpired)
            throw new GenericException("Token has expired", HttpStatus.INTERNAL_SERVER_ERROR);
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
        if (userRepository.findByEmail(email).isPresent())
            throw new GenericException("Email id already registered.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String forgotPassword(String email) throws GenericException {
        if (!Utility.isValidEmail(email))
            throw new GenericException("Invalid email", HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        if (!user.get().isActive())
            throw new GenericException("Your account is not active.", HttpStatus.INTERNAL_SERVER_ERROR);
        forgotPasswordHandler(user.get());
        return ApplicationConstants.SUCCESS_RESPONSE;
    }

    private void forgotPasswordHandler(User user) {
        AccountActivationToken accountActivationToken = new AccountActivationToken(user.getId());

        String body = "<html>\n" +
                "<body>Please use this token to reset you password.<br><br><h2>Token: " + accountActivationToken.getToken() + "</h2></body>\n" +
                "</html>";
        String subject = "Reset Your Password";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public String resetPassword(ResetPassword resetPassword) throws GenericException {
        Optional<User> user = userRepository.findByEmail(resetPassword.getEmail());
        if (!user.isPresent())
            throw new GenericException("Email id not found in database", HttpStatus.NOT_FOUND);

        AccountActivationToken accountActivationToken =
                AccountActivationToken.getAccountActivationTokenBean(resetPassword.getToken());

        checkForTokenValidityAndExpiry(accountActivationToken, 15);

        if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword()))
            throw new GenericException("Confirm password didn't matched", HttpStatus.BAD_REQUEST);

        user.get().setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        userRepository.save(user.get());

        passwordUpdateConfirmationEmailHandler(user.get());

        return ApplicationConstants.SUCCESS_RESPONSE;
    }

    @Override
    public String updatePassword(@Valid ResetPassword resetPassword) throws GenericException {
        if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword()))
            throw new GenericException("Confirm password didn't matched", HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findByEmail(resetPassword.getEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(resetPassword.getPassword()));
            userRepository.save(user.get());

            passwordUpdateConfirmationEmailHandler(user.get());

            return ApplicationConstants.SUCCESS_RESPONSE;
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    private void passwordUpdateConfirmationEmailHandler(User user) {
        String body = "<html>\n" +
                "<body>Dear " + user.getFirstName() + ",<br>Your password has been updated successfully.</body>\n" +
                "</html>";
        String subject = "Password Updated!";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public String reSendActivationLink(String email) throws GenericException {
        if (!Utility.isValidEmail(email))
            throw new GenericException("Invalid email", HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        }
        if (user.get().isActive()) {
            return "Your account is already active.";
        }
        accountActivationHandler(user.get());
        return ApplicationConstants.SUCCESS_RESPONSE;
    }

    @Override
    public UserDto getUserByEmail(String email) throws GenericException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())
            return user.get().toUserDto();
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<AddressDto> getAddressesByUserEmail(String email) throws GenericException {
        return getUserByEmail(email).getAddressList();
    }
}
