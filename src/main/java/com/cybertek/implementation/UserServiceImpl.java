package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import com.cybertek.exception.TicketingException;
import com.cybertek.mapper.Mapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.RoleRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;






    @Override
    public void save(String username) {




    }

    @Override
    public void save(UserDTO dto) {

        //UserEntity userEntity = userMapper.convertToUserEntity(dto);
//        dto.setEnabled(true);
//        UserEntity userEntity = mapper.convert(dto,new UserEntity());
//
//        int countDuplicate = userRepository.isDuplicate(dto.getUsername());
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//
//        if(countDuplicate==0)
//            userRepository.save(userEntity);

        dto.setEnabled(true);

        UserEntity obj =  mapper.convert(dto,new UserEntity());
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        userRepository.save(obj);




    }

    @Override
    public List<UserDTO> findAllUsers() {

        // get data from db then convert from persist data to dto
        // Mapper
        List<UserEntity> userEntity = userRepository.findAll();

        return userEntity.stream().map(entity->mapper.convert(entity,new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        UserEntity entity = userRepository.findByUsername(username);

        return mapper.convert(entity,new UserDTO());
    }

    @Override
    public void update(String username) {

    }

    @Override
    public void update(UserDTO userDTO) {
        String username = userDTO.getUsername();

        long id = userRepository.findByUsername(username).getId();

        UserEntity entity = mapper.convert(userDTO,new UserEntity());
        entity.setId(id);

        userRepository.save(entity);

    }

    @Override
    public void delete(String username) throws TicketingException {

        if(!checkAllowedToDelete(username)){
            throw new TicketingException("User "+username+" is not allowed to delete.");
        }

        UserEntity userEntity = userRepository.findByUsername(username);

        userEntity.setIsDeleted(true);
        long id = userEntity.getId();

        userRepository.save(userEntity);
    }

    @Override
    public List<UserDTO> findAllUsersByRole(String roleDescription) {

        List<UserEntity> entities = userRepository.findAllByRoleDescriptionIgnoreCase(roleDescription);

        return entities.stream().map(entity->mapper.convert(entity,new UserDTO())).collect(Collectors.toList());
    }


    @Override
    public boolean checkAllowedToDelete(String username) {

        UserDTO userDTO = findByUserName(username);

        switch (userDTO.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> entities = projectService.findAllUnCompletedProjectByUserName(username);
                return entities.size()==0;
            case "Employee":
                List<TaskDTO> entities1 = taskService.findAllUnCompletedTasksByUser(username);
                return entities1.size()==0;
            case "Admin":
                List<TaskDTO> entities2 = taskService.findAllUnCompletedTasksByUser(username);
                return entities2.size()==0;
        }

        return false;
    }
}
