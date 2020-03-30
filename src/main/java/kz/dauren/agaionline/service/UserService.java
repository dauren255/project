package kz.dauren.agaionline.service;

import kz.dauren.agaionline.models.Role;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public boolean addUser(User user){
        User userDb = userRepository.findByUsernameIgnoreCase(user.getUsername());
        if (userDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Object findById(Long id) {
        return userRepository.findById(id).get();
    }
}
