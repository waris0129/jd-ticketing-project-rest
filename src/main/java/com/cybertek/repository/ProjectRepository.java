package com.cybertek.repository;

import com.cybertek.entity.ProjectEntity;
import com.cybertek.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,String> {

    ProjectEntity findByProjectCode(String projectCode);
    List<ProjectEntity> findByAssignedManager(UserEntity userEntity);

    @Query("SELECT COUNT(p) FROM ProjectEntity p where p.projectCode = ?1 and p.isDeleted<>true")
    Integer isDuplicate(String projectCode);


    @Query("SELECT p from ProjectEntity p where p.projectStatus<> 'COMPLETE' and p.assignedManager.username=?1")
    List<ProjectEntity> findAllUnCompletedProjectByUserName(String username);
}
