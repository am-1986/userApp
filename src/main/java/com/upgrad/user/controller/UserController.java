package com.upgrad.user.controller;

import com.upgrad.user.dto.UserDTO;
import com.upgrad.user.entities.User;
import com.upgrad.user.service.UserService;
import com.upgrad.user.utils.POJOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user_app/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a POST Call
     */

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = POJOConvertor.covertUserDTOToEntity(userDTO);
        User savedUser = userService.createUser(user);
        UserDTO savedUserDTO = POJOConvertor.covertUserEntityToDTO(user);

        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> userList = userService.getAllUsers();

        List<UserDTO> userDTOList = new ArrayList<>();

        userList.forEach(u -> userDTOList.add(POJOConvertor.covertUserEntityToDTO(u)));

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") int id) {
        User user = userService.getUserBasedOnId(id);

        UserDTO userDTO = POJOConvertor.covertUserEntityToDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        User userToBeUpdated = POJOConvertor.covertUserDTOToEntity(userDTO);
        User savedUpdatedUser = userService.updateUser(userToBeUpdated);
        UserDTO userDTO1 = POJOConvertor.covertUserEntityToDTO(savedUpdatedUser);

        return new ResponseEntity<>(userDTO1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable(name = "id") int id) {
        User user = userService.getUserBasedOnId(id);

        userService.deleteUser(user);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}