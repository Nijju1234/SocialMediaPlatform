package com.scmd.socialmedia.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.scmd.socialmedia.entity.Likes;
import com.scmd.socialmedia.entity.Users;
import com.scmd.socialmedia.services.LikesService;
import com.scmd.socialmedia.services.UsersService;

@RestController
@RequestMapping("/api/likes")
public class LikesController {

	@Autowired
	LikesService likesService;
	
	@Autowired
	UsersService usersService;
	
 	//working
 // Retrieve all likes on posts created by a specific user
    @GetMapping("/{userid}/posts/likes")
    public ResponseEntity<Map<String, Object>> getLikesByUserId(@PathVariable("userid") int userId) {
        List<Likes> likes = likesService.getLikeBySpecfUser(userId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("data", likes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

 	//working
 	// Retrieve all likes given by a specific user
    @GetMapping("{userId}/likes")
    public ResponseEntity<Map<String, Object>> getLikesByUser(@PathVariable("userId") int userId) {
        Users userDto = usersService.getUserById(userId); // Get user as DTO
        List<Likes> likes = likesService.getLikesByUser(userDto); // Assuming you modify service method to accept UsersDTO
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("data", likes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
