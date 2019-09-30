package com.giobart.teamup.service;

import com.giobart.teamup.model.Role;
import com.giobart.teamup.repository.UserRepository;
import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${mentor.whitelist}")
    private String[] mentorWhitelist;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(2l).orElse(null));
        if(Arrays.stream(mentorWhitelist).filter(elem -> elem.equals(user.getEmail())).count()==1){
            roles.add(roleRepository.findById(3l).orElse(null));
        }
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername());

        if(user.getSkills()!=null){
            oldUser.setSkills(user.getSkills());
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

    @Override
    public User addRole(User user, Role role) {

        if(!roleRepository.findById(role.getId()).equals(role)){
            throw new IllegalArgumentException();
        }
        if(role.getId()==1){
            throw new IllegalArgumentException();
        }

        user.getRoles().add(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public User removeRole(User user, Role role) {

        if(!roleRepository.findById(role.getId()).equals(role)){
            throw new IllegalArgumentException();
        }
        if(role.getId()==1){
            throw new IllegalArgumentException();
        }

        user.getRoles().remove(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public void toggleMentor(User u, String username) {
        Role mentor = roleRepository.findById(3l).get();
        if(u.getRoles().contains(roleRepository.getOne(1l))){
            User user = userRepository.findByUsername(username);
            if(user.getRoles().contains(mentor)){
                user.getRoles().remove(mentor);
            }else {
                user.getRoles().add(mentor);
            }
            userRepository.save(user);
        }
    }
}
