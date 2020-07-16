package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    void createProject(ProjectDto projectDto);

    void updateProject(ProjectDto projectDto);

    void removeProject(String projectId);

    ProjectDto findByProjectId(String projectId);

    Page<ProjectDto> findAllByUserId(Long userId);

    

}