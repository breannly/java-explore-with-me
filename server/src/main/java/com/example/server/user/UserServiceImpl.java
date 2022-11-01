package com.example.server.user;

import com.example.server.user.dto.NewUserRequest;
import com.example.server.user.dto.UserDto;
import com.example.server.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public List<UserDto> getUsers(List<Long> userIds) {
		return userRepository.findUsersByIds(userIds)
				.stream()
				.map(UserMapper::mapToUserDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserDto createUser(NewUserRequest newUserRequest) {
		User user = UserMapper.mapToUser(newUserRequest);
		return UserMapper.mapToUserDto(userRepository.save(user));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
