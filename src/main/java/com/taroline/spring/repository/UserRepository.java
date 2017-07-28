package com.taroline.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taroline.spring.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public User findByMailAddress(String mailAddress);
    public User findById(Long id);
}
