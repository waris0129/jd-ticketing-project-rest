package com.cybertek.entity;


import com.cybertek.dto.UserDTO;
import com.cybertek.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "projects")
@Where(clause = "projectent0_.is_deleted=false")
public class ProjectEntity extends BaseEntity{

    private String projectName;

    private String projectCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserEntity assignedManager;

    @Column(columnDefinition = "DATE")
    private LocalDate projectStartDate;

    @Column(columnDefinition = "DATE")
    private LocalDate projectEndDate;

    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    private int completeTaskCounts;
    private int unfinishedTaskCounts;

}
