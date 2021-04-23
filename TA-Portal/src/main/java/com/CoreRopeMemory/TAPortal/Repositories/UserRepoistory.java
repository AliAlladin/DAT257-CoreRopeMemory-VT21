package com.CoreRopeMemory.TAPortal.Repositories;

import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoistory extends JpaRepository<User, String> {
}
