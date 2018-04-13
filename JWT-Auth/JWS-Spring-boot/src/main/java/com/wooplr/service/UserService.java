package com.wooplr.service;

import com.wooplr.dto.UserDTO;

public interface UserService {

	UserDTO getUser(Long id) throws Exception;

	UserDTO createUser(UserDTO userDTO) throws Exception;

	UserDTO modifyUser(UserDTO userDTO) throws Exception;

	String deleteUser(Long id) throws Exception;
}
