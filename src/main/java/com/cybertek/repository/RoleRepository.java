package com.cybertek.repository;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

}
