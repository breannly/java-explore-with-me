package com.example.server.user;

import com.example.server.user.dto.NewUserRequest;
import com.example.server.user.dto.UserDto;

import java.util.List;

public interface UserService {

	List<UserDto> getUsers(List<Long> userIds);

	UserDto createUser(NewUserRequest newUserRequest);

	void deleteUser(Long userId);
}
