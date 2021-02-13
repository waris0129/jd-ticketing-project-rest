package com.cybertek.controller;

import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.TaskEntity;
import com.cybertek.enums.Status;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repository.TaskRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects",projectService.findAllProjectList());
        model.addAttribute("employees", userService.findAllUsersByRole("employee"));
        model.addAttribute("taskList",taskService.findAllUnCompletedTasks());

        return "task/create";
    }


    @PostMapping("/create")
    public String addTask(TaskDTO task){


        task.setAssignDate(LocalDate.now());
        task.setStatus(Status.OPEN);
        task.setId(UUID.randomUUID().getMostSignificantBits());
        taskService.save(task);

        return "redirect:/task/create";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id")String id){
        taskService.delete(Long.parseLong(id));
        return "redirect:/task/create";
    }

    @GetMapping("/update/{id}")
    public String updateTask(@PathVariable("id") String id,Model model){
        var test = taskService.findTaskById(Long.parseLong(id));

        model.addAttribute("task", taskService.findTaskById(Long.parseLong(id)));
        model.addAttribute("projects",projectService.findAllProjectList());
        model.addAttribute("employees", userService.findAllUsersByRole("employee"));
        model.addAttribute("taskList",taskService.findAllUnCompletedTasks());

        return "task/update";
    }


    @PostMapping("/update/{id}")
    public String saveUpdate(@PathVariable("id") String id, TaskDTO task){

        var pre = taskService.findTaskById(Long.parseLong(id));

        task.setStatus(pre.getStatus());
        task.setAssignDate(pre.getAssignDate());
        taskService.update(task);

        return "redirect:/task/create";
    }


    @GetMapping("employee/pending-tasks")
    public String pendingTasks(Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("test",new TaskDTO());
                                        //FIND UNCompleted tasks
    //    List<TaskDTO> tasks = taskService.findAllTaskListByUser("mike@gmail.com");
        List<TaskDTO> tasks = taskService.findAllUnCompletedTasksByUser(username);
        model.addAttribute("tasks",tasks);



        return "task/pending-tasks";
    }


    @GetMapping("employee/pending-tasks/{id}")
    public String updatePendingTaskStatus(@PathVariable("id")Long id, Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        TaskDTO taskDTO = taskService.findTaskById(id);

        model.addAttribute("test",taskDTO);

        List<TaskDTO> tasks = taskService.findAllUnCompletedTasksByUser(username);

        model.addAttribute("tasks",tasks);


        return "/task/update-pending-tasks";

    }



    @PostMapping("employee/pending-tasks/{id}")
    public String savePendingTaskStatus(@PathVariable("id") Long id, TaskDTO taskDTO){

        TaskDTO  taskDTO1 = taskService.findTaskById(id);

        taskDTO1.setStatus(taskDTO.getStatus());

        taskService.update(taskDTO1);

        return "redirect:/task/employee/pending-tasks";
    }



    @GetMapping("/employee/archive")
    public String archive(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TaskDTO> tasks = taskService.findAllCompletedTaskListByUser(username);

        model.addAttribute("tasks",tasks);

        return "task/archive";
    }








}





















