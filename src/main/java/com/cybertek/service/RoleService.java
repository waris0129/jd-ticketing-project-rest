package com.cybertek.service;


import com.cybertek.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAllRoles();
    RoleDTO findByRoleId(Long id);


}
