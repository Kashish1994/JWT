package com.wooplr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wooplr.dto.UserDTO;
import com.wooplr.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/public/login", method = RequestMethod.POST)
	public void login() {
		System.out.println("Logging IN");
	}

	@RequestMapping(value = "/public/user/{userId}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable("userId") Long userID) throws Exception {
		System.out.println("Getting User");
		return userService.getUser(userID);
	}

	@RequestMapping(value = "/internal/user/create", method = RequestMethod.POST)
	public UserDTO createUser(@RequestBody UserDTO userDTO) throws Exception {
		System.out.println("Creating User");
		return userService.createUser(userDTO);
	}

	@RequestMapping(value = "/internal/user/delete/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") Long id) throws Exception {
		System.out.println("Deleting User");
		return userService.deleteUser(id);
	}

	@RequestMapping(value = "/internal/user/modify", method = RequestMethod.PUT)
	public UserDTO modifyUser(@RequestBody UserDTO userDTO) throws Exception {
		System.out.println("Modifying User");
		return userService.modifyUser(userDTO);
	}
}
