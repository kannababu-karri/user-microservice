package com.restful.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restful.user.entities.User;
import com.restful.user.exception.InvalidUserException;
import com.restful.user.service.UserService;
import com.restful.user.utils.StringUtility;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/user")
public class LoginController {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    // GET products by multiple fields
    @GetMapping("/validate/user")
    public ResponseEntity<User> getByValidateUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) throws Exception {
    	
    	_LOGGER.info(">>> Inside getById. <<<");
    	_LOGGER.info(">>> Username. <<<"+username);
    	_LOGGER.info(">>> Password. <<<"+password);
    	
    	User user = null;
    	
        if(StringUtility.isEmpty(username) && StringUtility.isEmpty(password)) {
        	throw new InvalidUserException("Enter valid User Id and Password.");
        } else if(StringUtility.isEmpty(username)) {
        	throw new InvalidUserException("Enter valid User Id.");
    	} else if(StringUtility.isEmpty(password)) {
    		throw new InvalidUserException("Enter valid User Id.");
    	} else {
	        user = userService.findByUserNameAndPassword(username, password)
	        		.orElseThrow(() -> new InvalidUserException("User not found with Username: " + username));
	        
        	if(user != null && user.getUserId() > 0) {
        		_LOGGER.info(">>> user.getUserId() > 0. <<<"+user.getUserId());
        		return ResponseEntity.ok(user);
        	} else {
        		_LOGGER.info(">>> No user in the system. <<<");
        	}
    	}
        return ResponseEntity.ok(user);
    }
}
