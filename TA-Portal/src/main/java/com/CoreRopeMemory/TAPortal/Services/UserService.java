package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.UserRepoistory;
import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepoistory userRepoistory;

    public void save(User user){
        userRepoistory.save(user);
    }

    public User get(String pNumber){
        return userRepoistory.getOne(pNumber);
    }

    public boolean isEmpty() {
        if (userRepoistory.count() == 0){
            return true;
        }
        return false;
    }
}
