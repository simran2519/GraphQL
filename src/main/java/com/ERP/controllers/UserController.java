package com.ERP.controllers;

import com.ERP.dtos.UserDto;
import com.ERP.inputs.UserInput;
import com.ERP.services.UserService;
import com.ERP.utils.MyResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController
{
    UserService userService;
    ObjectMapper objectMapper;
    public UserController(UserService userService,ObjectMapper objectMapper)
    {
        this.userService=userService;
        this.objectMapper=objectMapper;
    }

    @MutationMapping()
    public UserDto addUser( @Argument UserInput userInput)
    {
        UserDto userDto =objectMapper.convertValue(userInput,UserDto.class);
        userDto.setName(userInput.getName());
        userDto.setEmail(userInput.getEmail());
        userDto.setPassword(userInput.getPassword());
        userDto.setPhone(userInput.getPhone());
        UserDto addedUser=userService.addUser(userDto);
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(addedUser);

            return addedUser;

//        else
//        {
//            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST,false,"Something went wrong",addedUser);
//        }
    }
    @MutationMapping("deleteUser")
    public ResponseEntity<Object> deleteUser(@Argument Long userId) {
        UserDto userDto= userService.deleteUser(userId);
        if(userDto!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.OK,true,"User is Deleted Successfully",userDto);
        }
        else {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST,false,"User is not Deleted Successfully",userDto);
        }
    }

    @MutationMapping
    public List<UserDto> addAllUsers(@Argument List<UserInput> userInputs) {
        List<UserDto> userDtos = userInputs.stream().map(input -> {
            UserDto userDto = new UserDto();
            userDto.setName(input.getName());
            userDto.setEmail(input.getEmail());
            userDto.setPassword(input.getPassword());
            userDto.setPhone(input.getPhone());
            return userDto;
        }).toList();
        return userService.addAllUser(userDtos);
    }

    @QueryMapping
    public ResponseEntity<Object> findUser(@Argument Long userId) {
        UserDto userTofind =userService.findUser(userId);
        if(userTofind!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.OK,true,"User is found", userTofind);
        }
        else {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND,false,"User is not founc",userTofind);
        }
    }

    @QueryMapping("findAllUsers")
    public List<UserDto> findAllUsers() {
        return userService.findAllUser();
    }

    @MutationMapping
    public ResponseEntity<Object> updateUser(@Argument Long userId, @Argument UserInput userInput) {
        UserDto userDto = new UserDto();
        userDto.setName(userInput.getName());
        userDto.setEmail(userInput.getEmail());
        userDto.setPassword(userInput.getPassword());
        userDto.setPhone(userInput.getPhone());
        UserDto userDto1= userService.updateUser(userDto,userId);
        if(userDto1!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.OK,true,"User is updated successfully", userDto1);
        }
        else {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST,false,"Something went wrong and User is not updated successfully",userDto1);
        }
    }
}
