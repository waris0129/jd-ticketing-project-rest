package com.cybertek.dto;


import com.cybertek.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class TaskDTO {

    private Long id;
    private ProjectDTO project;
    private UserDTO employee;
    private String taskSubject;
    private String taskDetail;
    private LocalDate assignDate;
    private Status status;

    public TaskDTO(ProjectDTO project, UserDTO employee, String taskSubject, String taskDetail, Status status, LocalDate assignDate) {
        this.project = project;
        this.employee = employee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.assignDate = assignDate;
        this.status = status;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }
}
