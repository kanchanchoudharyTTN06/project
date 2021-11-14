package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate/account")
    public ResponseEntity<Object> activateUser(@RequestParam("token") String token) throws GenericException {
        String msg = userService.activateUserAccount(token);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() throws GenericException {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") int id) throws GenericException {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/send/activation/link")
    public ResponseEntity<Object> reSendActivationLink(@RequestBody ResetPassword resetPassword) throws GenericException {
        String response = userService.reSendActivationLink(resetPassword.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<Object> forgotPassword(@RequestBody ResetPassword resetPassword) throws GenericException {
        String status = userService.forgotPassword(resetPassword.getEmail());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/reset/password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPassword resetPassword) throws GenericException {
        String status = userService.resetPassword(resetPassword);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
