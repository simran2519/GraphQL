package com.ERP.servicesInter;

import com.ERP.dtos.UserDto;

import java.util.List;

public interface UserServiceInter
{
     UserDto addUser(UserDto userDto);
     UserDto updateUser(UserDto userDto, long userId);
     UserDto deleteUser(long userId);
     UserDto findUser(long userId);
     List<UserDto> addAllUser(List<UserDto> userDtos);
     List<UserDto> findAllUser();
}
