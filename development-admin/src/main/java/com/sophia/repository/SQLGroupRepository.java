package com.sophia.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SqlGroup;

@Repository
public interface SQLGroupRepository extends JpaRepository<SqlGroup, String>{
	
	public Page<SqlGroup> findAll(Pageable pageable);
}

