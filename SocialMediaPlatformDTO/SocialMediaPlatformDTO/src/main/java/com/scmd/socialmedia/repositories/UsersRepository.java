package com.scmd.socialmedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scmd.socialmedia.entity.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Add custom query methods if needed, for example:
	Optional<Users> findByEmail(String email);

	Optional<Users> findByUsername(String username);
	
	Optional<Users> findByEmailAndPassword(String email,String password);
}