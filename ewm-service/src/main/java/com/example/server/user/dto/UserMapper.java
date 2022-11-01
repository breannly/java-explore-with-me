package com.example.server.user.dto;

import com.example.server.user.User;

public class UserMapper {

	public static User mapToUser(NewUserRequest newUserRequest) {
		User user = new User();
		user.setEmail(newUserRequest.getEmail());
		user.setName(newUserRequest.getName());
		return user;
	}

	public static UserDto mapToUserDto(User user) {
		return new UserDto(
				user.getId(),
				user.getEmail(),
				user.getName()
		);
	}
}
