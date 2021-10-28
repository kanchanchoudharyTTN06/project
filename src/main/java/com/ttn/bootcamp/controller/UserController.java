package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@RequestBody UserDto userDto) throws GenericException {
        UserDto user = userService.registerUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/activate/account")
    public ResponseEntity<Object> activateUser(@RequestParam("token") String token) throws GenericException {
        String msg = userService.activateUserAccount(token);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") int id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
