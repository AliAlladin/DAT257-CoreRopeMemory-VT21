package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.UserRepoistory;
import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User
 */

@Service
public class UserService {
    /**
     * Injected User Repository
     */
    @Autowired
    private UserRepoistory userRepoistory;

    /**
     * Saves a user to the database
     * @param user the user to save
     */
    public void save(User user){
        userRepoistory.save(user);
    }

    /**
     * Gets the user with a specific personal number
     * @param pNumber the personal number of the user
     * @return the user
     */
    public User get(String pNumber){
        return userRepoistory.getOne(pNumber);
    }

    /**
     * Checks if the user table in the database is empty
     * @return true if the are no users in the database
     */
    public boolean isEmpty() {
        if (userRepoistory.count() == 0){
            return true;
        }
        return false;
    }
}
