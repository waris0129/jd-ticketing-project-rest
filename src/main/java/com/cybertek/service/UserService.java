package com.cybertek.service;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import com.cybertek.exception.TicketingException;

import java.util.List;

public interface UserService {

        List<UserDTO> findAllUsers();
        UserDTO findByUserName(String username);
        void update(String username);
        void update(UserDTO userDTO);
        void delete(String username) throws TicketingException;
        void  save(String username);
        UserDTO save(UserDTO dto) throws TicketingException;

        List<UserDTO> findAllUsersByRole(String roleDescription);

        boolean checkAllowedToDelete(String username);

        UserDTO confirm(UserEntity userEntity);
}
