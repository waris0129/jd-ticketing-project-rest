package com.cybertek.repository;


import com.cybertek.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {


     List<TaskEntity> findAllByProjectProjectCode(String projectCode);

     @Query("SELECT COUNT(t) FROM TaskEntity t where t.project.projectCode=?1 and t.status='COMPLETE' and t.isDeleted=false ")
     Integer totalCompletedTasks(String projectCode);

     @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on p.id = t.project_id WHERE p.project_code = ?1 and t.status <> 'COMPLETE' and t.is_deleted=false",nativeQuery = true)
     Integer totalNonCompleteTasks(String projectCode);


     @Query("SELECT t FROM TaskEntity t where t.employee.username=?1")
     List<TaskEntity> findAllTaskListByUser(String username);

    @Query("SELECT t FROM TaskEntity t where t.status='COMPLETE' AND t.employee.username=?1")
    List<TaskEntity> findAllCompletedTaskListByUser(String username);


    @Query("SELECT t FROM TaskEntity t where t.status<>'COMPLETE' AND t.employee.username=?1")
    List<TaskEntity> findAllUnCompletedTasksByUser(String username);

    @Query("SELECT t FROM TaskEntity t where t.status<>'COMPLETE'")
    List<TaskEntity> findAllUnCompletedTasks();

}
