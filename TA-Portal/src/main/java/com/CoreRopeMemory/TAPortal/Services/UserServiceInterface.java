package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {
    User save(User user);
}
