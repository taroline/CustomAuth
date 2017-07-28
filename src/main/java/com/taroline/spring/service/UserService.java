package com.taroline.spring.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taroline.spring.entity.User;
import com.taroline.spring.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("username is empty");
        }

        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }

        return user;
    }

    public Iterable<User> findAllUser() {
        return repository.findAll();
    }

    public User findUserById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void registerAdmin(String username, String password, String mailAddress) {
        User user = new User(username, this.passwordEncoder.encode(password), mailAddress);
        user.setAdmin(true);
        repository.save(user);
    }

    @Transactional
    public void registerUser(String username, String password, String mailAddress) {
        User user = new User(username, this.passwordEncoder.encode(password), mailAddress);
        repository.save(user);
    }

}
