package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.UserRepository;
import com.CoreRopeMemory.TAPortal.model.Role;
import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service class for User
 */

@Service
public class UserService implements UserServiceInterface{
    /**
     * Injected User Repository
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Saves a user to the database
     * @param user the user to save
     */
    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        userRepository.save(user);
        return user;
    }

    public void saveUserDetails(User user){
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /**
     * Gets the user with a specific personal number
     * @param pNumber the personal number of the user
     * @return the user
     */
    public User get(String pNumber){
        return userRepository.getOne(pNumber);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Checks if the user table in the database is empty
     * @return true if the are no users in the database
     */
    public boolean isEmpty() {
        if (userRepository.count() == 0){
            return true;
        }
        return false;
    }
}
