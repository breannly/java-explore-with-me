package com.example.server.user.dto;

import com.example.server.user.User;

public class UserMapper {

	public static User mapToUser(NewUserRequest newUserRequest) {
		User user = new User();
		user.setEmail(newUserRequest.getEmail());
		user.setName(newUserRequest.getName());
		return user;
	}

	public static UserShortDto mapToUserShortDto(User user) {
		return new UserShortDto(
				user.getId(),
				user.getName()
		);
	}

	public static UserDto mapToUserDto(User user) {
		return new UserDto(
				user.getId(),
				user.getEmail(),
				user.getName()
		);
	}
}
