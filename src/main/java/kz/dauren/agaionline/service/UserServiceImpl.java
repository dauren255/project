package kz.dauren.agaionline.service;

import kz.dauren.agaionline.models.Post;
import kz.dauren.agaionline.models.Role;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.repo.UserRepository;
import kz.dauren.agaionline.service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Transactional
    public User findByUsernameIgnoreCase(String username){
        return userRepository.findByUsernameIgnoreCase(username);
    }
    @Transactional
    public Iterable<User> findAllByPostsContains(Post post){
        return userRepository.findAllByPostsContains(post);
    }
    @Transactional
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
    @Transactional
    public Iterable<User> findAll() {
//        Pageable limit = PageRequest.of(0,3);
//        return userRepository.findAll(limit);
        return userRepository.findAllByOrderById();
    }
    @Transactional
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    @Transactional
    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
    @Transactional
    public void delete(Long id){
        userRepository.delete(userRepository.findById(id).get());
    }
}
