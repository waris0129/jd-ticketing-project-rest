package com.cybertek.service;

import com.cybertek.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    List<TaskDTO> findAllTaskList();
    TaskDTO findTaskById(long id);
    void update(long id);
    void update(TaskDTO dto);
    void save(long id);
    void save(TaskDTO dto);
    void delete(long id);
    void delete(TaskDTO dto);

    int totalNonCompleteTasks(String  projectCode);
    int totalCompletedTasks(String projectCode);

    List<TaskDTO> findAllTaskListByUser(String username);
    List<TaskDTO> findAllCompletedTaskListByUser(String username);
    List<TaskDTO> findAllUnCompletedTasksByUser(String username);
    List<TaskDTO> findAllUnCompletedTasks();


}
