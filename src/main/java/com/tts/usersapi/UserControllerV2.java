package com.tts.usersapi;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value = "users", description = "Operations pertaining to Users.")
@RequestMapping("/v2")
public class UserControllerV2 {
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get all users or users by state.", response = User.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved users"),
            @ApiResponse(code = 401, message = "You are not authorized to update a user"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 400, message = "Something went wrong with the request (state must be specified)") })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "state", required = false) String state) {
        if (state == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<User> ustate = userRepository.findByState(state);
        return new ResponseEntity<>(ustate, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a user by ID.", response = User.class, responseContainer = "List")

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") Long id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new user.", response = User.class, responseContainer = "List")

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a user.", response = User.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated the user"),
            @ApiResponse(code = 401, message = "You are not authorized to update a user"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 400, message = "Something went wrong with the request") })
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid User user,
            BindingResult bindingResult) {

        if (userRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a user.", response = User.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the user"),
            @ApiResponse(code = 401, message = "You are not authorized to delete a user"),
            @ApiResponse(code = 404, message = "User not found") })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}