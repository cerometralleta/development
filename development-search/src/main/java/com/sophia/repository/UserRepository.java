package com.sophia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	 
}
