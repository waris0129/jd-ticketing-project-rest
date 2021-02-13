package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.enums.Status;
import com.cybertek.service.ProjectService;
import com.cybertek.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/create")
    public String projectList(Model model){

        model.addAttribute("projects",projectService.findAllProjectList());
        model.addAttribute("managers",userService.findAllUsersByRole("manager"));
        model.addAttribute("project", new ProjectDTO());


        return "project/create";
    }


    @PostMapping("/create")
    public String addProject(@ModelAttribute("project") ProjectDTO projectDTO){

        projectDTO.setProjectStatus(Status.OPEN);

        projectService.save(projectDTO);

        return "redirect:/project/create";
    }


    @GetMapping("/update/{projectCode}")
    public String updateProject(@PathVariable("projectCode") String projectCode, Model model){


        model.addAttribute("project", projectService.findProjectByProjectCode(projectCode));
        model.addAttribute("projects",projectService.findAllProjectList());
        model.addAttribute("managers",userService.findAllUsersByRole("manager"));


        return "project/update";
    }



    @PostMapping("/update/{projectCode}")
    public String updateSave(@PathVariable("projectCode") String projectCode,@ModelAttribute("project") ProjectDTO projectDTO){

        projectDTO.setProjectStatus(projectService.findProjectByProjectCode(projectCode).getProjectStatus());


        projectService.update(projectDTO);


        return "redirect:/project/create";
    }


    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode){

        projectService.delete(projectCode);


        return "redirect:/project/create";
    }

    @GetMapping("complete/{projectCode}")
    public String projectComplete(@PathVariable("projectCode") String projectCode){

        var project = projectService.findProjectByProjectCode(projectCode);

        project.setProjectStatus(Status.COMPLETE);

        projectService.update(project);

        return "redirect:/project/create";
    }


    @GetMapping("/manager/complete")
    public String projectStatus(Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ProjectDTO> projects = projectService.findProjectListByUserName(username);



        model.addAttribute("projects",projects);



        return "manager/project-status";
    }




}
