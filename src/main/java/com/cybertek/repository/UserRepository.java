package com.cybertek.repository;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

        UserEntity findByUsername(String username);

        List<UserEntity> findAllByRoleDescriptionIgnoreCase(String description);

        @Query("SELECT COUNT(u) FROM UserEntity u where u.username=?1 and u.isDeleted<>true")
        Integer isDuplicate(String username);
}
