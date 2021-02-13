package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.ProjectEntity;
import com.cybertek.entity.TaskEntity;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.repository.TaskRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public List<TaskDTO> findAllTaskList() {

        List<TaskEntity> taskEntities = taskRepository.findAll();

        return taskEntities.stream().map(taskMapper::convertToTaskDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findTaskById(long id) {

        TaskEntity entity = taskRepository.findById(id).get();

        return taskMapper.convertToTaskDTO(entity);
    }

    @Override
    public void update(long id) {


    }

    @Override
    public void update(TaskDTO dto) {
        TaskEntity taskEntity = taskRepository.findById(dto.getId()).get();
        long id = taskEntity.getId();
        long prjId = taskEntity.getProject().getId();


        TaskEntity taskEntity1 = taskMapper.convertToTaskEntity(dto);
        taskEntity1.getProject().setId(prjId);
        taskEntity1.setId(id);

        taskRepository.save(taskEntity1);
    }

    @Override
    public void save(long id) {

    }

    @Override
    public void save(TaskDTO dto) {

        ProjectEntity projectEntity = projectRepository.findByProjectCode(dto.getProject().getProjectCode());
        long projectId = projectEntity.getId();

        TaskEntity taskEntity = taskMapper.convertToTaskEntity(dto);
        taskEntity.getProject().setId(projectId);

        taskRepository.save(taskEntity);
    }

    @Override
    public void delete(long id) {
        TaskEntity taskEntity = taskRepository.findById(id).get();

        taskEntity.setIsDeleted(true);

        taskRepository.save(taskEntity);
    }

    @Override
    public void delete(TaskDTO dto) {

    }

    @Override
    public int totalNonCompleteTasks(String projectCode) {

        return taskRepository.totalNonCompleteTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public List<TaskDTO> findAllTaskListByUser(String username) {

        List<TaskEntity> entities=taskRepository.findAllTaskListByUser(username);

        List<TaskDTO> taskDTOList= entities.stream().map(taskMapper::convertToTaskDTO).collect(Collectors.toList());

        return taskDTOList;
    }

    @Override
    public List<TaskDTO> findAllCompletedTaskListByUser(String username) {

        List<TaskEntity> taskEntities = taskRepository.findAllCompletedTaskListByUser(username);

        return taskEntities.stream().map(taskMapper::convertToTaskDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllUnCompletedTasksByUser(String username) {

        return taskRepository.findAllUnCompletedTasksByUser(username).stream()
                .map(taskMapper::convertToTaskDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskDTO> findAllUnCompletedTasks() {
        return taskRepository.findAllUnCompletedTasks().stream()
                .map(taskMapper::convertToTaskDTO)
                .collect(Collectors.toList());
    }
}
