package com.giobart.teamup.service;

import com.giobart.teamup.model.Role;
import com.giobart.teamup.repository.UserRepository;
import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(2l).orElse(null));
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername());

        if(user.getName()!=null){
            oldUser.setName(user.getName());
        }
        if(user.getSurname()!=null){
            oldUser.setSurname(user.getSurname());
        }
        if(user.getSkills()!=null){
            oldUser.setSkills(user.getSkills());
        }
        if(user.getEmail()!=null){
            oldUser.setEmail(user.getEmail());
        }

        userRepository.save(oldUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
