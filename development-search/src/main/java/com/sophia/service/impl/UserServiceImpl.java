package com.sophia.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.sophia.domain.User;
import com.sophia.repository.UserRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.UserService;

@Service
public class UserServiceImpl extends JpaRepositoryImpl<UserRepository> implements UserService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//调用本地SQL
	@PersistenceContext
	private EntityManager entityManager;
	
	public void nativeQuery(){
		Query query = entityManager.createNativeQuery("select * from TB_AT_USER",User.class);
		List<User> data = query.getResultList();
		for(User user : data){
			System.out.println(user.getAccount());
		}
	}	

}
