package com.phucx.phucxfoodshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phucx.phucxfoodshop.compositeKey.UserRoleID;
import com.phucx.phucxfoodshop.model.UserDetails;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UserRoleID> {
    Page<UserDetails> findByRoleName(String roleName, Pageable pageable);
}
