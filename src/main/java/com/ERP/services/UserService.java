package com.ERP.services;

import com.ERP.dtos.UserDto;
import com.ERP.entities.User;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.UserRepository;
import com.ERP.servicesInter.UserServiceInter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserServiceInter
{
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper)
    {
        this.userRepository=userRepository;
        this.objectMapper=objectMapper;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        try {
            User newUser = objectMapper.convertValue(userDto, User.class);
            User savedUser=userRepository.save(newUser);
            return objectMapper.convertValue(savedUser, UserDto.class);
        } catch (Exception e) {
            throw new IdNotFoundException("Error adding user: " + e.getMessage());
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        try {
            //get the user which we need to update
            User user= userRepository.findById(userId).orElseThrow(()-> new IdNotFoundException("User not found with id: "+userId));

            // Update user fields if they are not null or empty in userDto
            if (Objects.nonNull(userDto.getName()) && !userDto.getName().isEmpty()) {
                user.setName(userDto.getName());
            }
            if (Objects.nonNull(userDto.getEmail()) && !userDto.getEmail().isEmpty()) {
                user.setEmail(userDto.getEmail());
            }
            if (Objects.nonNull(userDto.getPhone()) && !userDto.getPhone().isEmpty()) {
                user.setPhone(userDto.getPhone());
            }
            if (Objects.nonNull(userDto.getPassword()) && !userDto.getPassword().isEmpty()) {
                user.setPassword(userDto.getPassword());
            }
            userRepository.save(user);
            return objectMapper.convertValue(user, UserDto.class);
            }
        catch (Exception e) {
            throw new IdNotFoundException("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public UserDto deleteUser(long userId) {
        try {
            User userToDelete = userRepository.findById(userId)
                    .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));
            userRepository.deleteById(userId);
            return objectMapper.convertValue(userToDelete, UserDto.class);
        } catch (Exception e) {
            throw new IdNotFoundException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public UserDto findUser(long userId) {
        try {
            User userToSearch = userRepository.findById(userId)
                    .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.convertValue(userToSearch, UserDto.class);
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding user: " + e.getMessage());
        }
    }

    @Override
    public List<UserDto> addAllUser(List<UserDto> userDtos) {
        try {
            List<User> userList = Arrays.asList(objectMapper.convertValue(userDtos, User[].class));
            userRepository.saveAll(userList);
            return Arrays.asList(objectMapper.convertValue(userDtos, UserDto[].class));
        } catch (Exception e) {
            throw new IdNotFoundException("Error adding all users: " + e.getMessage());
        }
    }

    @Override
    public List<UserDto> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            return Arrays.asList(objectMapper.convertValue(userList, UserDto[].class));
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all users: " + e.getMessage());
        }
    }











}
