package com.wooplr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wooplr.domain.user.User;
import com.wooplr.dto.UserDTO;
import com.wooplr.repositories.UserRepository;
import com.wooplr.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;

	@Override
	public UserDTO getUser(Long id) throws Exception {
		User user = repo.getOne(id);
		if (user == null) {
			throw new Exception("No User Found with ID " + id);
		}
		UserDTO userDTOResponse = new UserDTO();
		userDTOResponse.setEmail(user.getEmail());
		userDTOResponse.setMobileNumber(user.getMobileNumber());
		userDTOResponse.setName(user.getName());
		return userDTOResponse;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) throws Exception {
		// Do normal data validations

		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setName(userDTO.getName());
		repo.save(user);

		System.out.println("Data Saved");
		return userDTO;
	}

	@Override
	public UserDTO modifyUser(UserDTO userDTO) throws Exception {
		User user = repo.getOne(userDTO.getId());
		if (user == null) {
			throw new Exception("No User Found with id " + userDTO.getId());
		}
		user.setEmail(userDTO.getEmail());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setName(userDTO.getName());
		repo.save(user);
		return userDTO;
	}

	@Override
	public String deleteUser(Long id) throws Exception {
		repo.delete(id);
		return "User Deleted";
	}
}
