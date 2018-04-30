package com.yuen.springapi.controllers;

import com.yuen.springapi.entities.Users;
import com.yuen.springapi.exceptions.InvalidRequestBodyException;
import com.yuen.springapi.pojo.ApiResponse;
import com.yuen.springapi.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Function;
import java.util.stream.Collectors;


@Api(description = "User Service")
@RestController
@RequestMapping("api/v1/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiOperation( "find all users" )
    @GetMapping
    public ResponseEntity findAllUsers(){
        return ResponseEntity.status(200).body(usersService.findAllUsers());
    }

    @ApiOperation( "add users" )
    @PostMapping
    public ResponseEntity addUser(@RequestBody @Valid  Users user, Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidRequestBodyException( getValidationErrors.apply(errors) );
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(usersService.addUser(user));
    }

    @ApiOperation( "find user by id" )
    @GetMapping("/{id}")
    public ResponseEntity findUser(@PathVariable Long id){
        return ResponseEntity.status(200).body(usersService.findUser(id));
    }

    @ApiOperation( "update user by id")
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody @Valid Users users, Errors errors){
        if (errors.hasErrors())
            throw new InvalidRequestBodyException( getValidationErrors.apply(errors) );
        return ResponseEntity.status(200).body(usersService.updateUser(id, users));
    }

    @ApiOperation( "delete user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if (usersService.deleteUser(id)){
            return ResponseEntity.status(200).body(ApiResponse.builder().code(200)
                    .status("OK").message("data at id "+id+" successfully deleted").build());
        }
        return ResponseEntity.status(400).body(ApiResponse.builder().code(400).
                status("BAD REQUEST").message("something went wrong").build());
    }


    private Function<Errors,String> getValidationErrors = err ->
            err.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
}
