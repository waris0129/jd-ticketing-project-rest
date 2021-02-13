package com.cybertek.implementation;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.RoleEntity;
import com.cybertek.mapper.RoleMapper;
import com.cybertek.repository.RoleRepository;
import com.cybertek.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;



    @Override
    public List<RoleDTO> findAllRoles() {

        List<RoleEntity> roleEntity = roleRepository.findAll();

        return roleEntity.stream().map(roleMapper::convertToRoleDto).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findByRoleId(Long id) {
        return null;
    }
}
