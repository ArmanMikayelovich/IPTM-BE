package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.ProjectDto;
import com.energizeglobal.itpm.model.dto.ProjectVersionDto;
import com.energizeglobal.itpm.model.enums.ProjectVersionStatus;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.ProjectVersionService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private static final Logger log = Logger.getLogger(ProjectController.class);

    private final ProjectService projectService;
    private final ProjectVersionService versionService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,})
    public void createProject(@RequestBody ProjectDto projectDto) {
        log.trace("creating project: " + projectDto);
        projectService.createProject(projectDto);
        log.trace("project created : " + projectDto);

    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateProject(@RequestBody ProjectDto projectDto) {
        log.trace("updating project: " + projectDto);
        projectService.updateProject(projectDto);
        log.trace("project updated : " + projectDto);
    }

    @GetMapping(value = "/by-id/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ProjectDto findById(@PathVariable("projectId") String projectId) {
        log.trace("Searching project by id: " + projectId);
        final ProjectDto byId = projectService.findById(projectId);
        log.trace("Project with id: " + projectId + " found." + byId);
        return byId;
    }

    @DeleteMapping(value = "/{projectId}")
    public void delete(@PathVariable("projectId") String projectId) {
        projectService.removeProject(projectId);
        log.trace("project with id: " + projectId + " removed.");

    }

    @GetMapping(value = "/by-user/{userId}")
    public Page<ProjectDto> findAllByUser(@PathVariable("userId") String userId, @RequestParam(required = false) final Pageable pageable) {
        log.trace("searching projects by userId: " + userId + " || pageable: " + pageable);
        return projectService.findAllByAssignedUserId(userId, pageable);
    }

    @GetMapping(value = "/{projectId}/versions")
    public List<ProjectVersionDto> getProjectVersions(@PathVariable("projectId") String projectId,
                                                      @RequestParam(required = false) ProjectVersionStatus status,
                                                      @RequestParam(name = "sort", required = false) String sortProperty,
                                                      @RequestParam(required = false) String direction) {
        log.trace("searching project version of project with id: " + projectId);
        if (status == null || sortProperty == null || direction == null) {
            return versionService.findAllByProjectId(projectId);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(direction), sortProperty);
            return versionService.findAllByProjectIdAndStatus(projectId, status, sort);
        }
    }

    @GetMapping(value = "/versions/{versionId}")
    public ProjectVersionDto getProjectVersionById(@PathVariable Long versionId) {
        log.trace("Searching version with id: " + versionId);
        return versionService.findById(versionId);
    }


    @PostMapping(value = "/{projectId}/versions")
    public void addProjectVersion(@RequestBody ProjectVersionDto versionDto,
                                  @PathVariable("projectId") String projectId) {

        log.trace("adding new version to project:" + projectId);
        versionDto.setProjectId(projectId);

        versionService.createNewVersion(versionDto);
    }

    @PutMapping(value = "/{projectId}/versions")
    public void updateProjectVersion(@RequestBody ProjectVersionDto versionDto,
                                     @PathVariable("projectId") String projectId) {

        log.trace("updating version of project:" + projectId);
        versionDto.setProjectId(projectId);
        versionService.update(versionDto);
    }

    @DeleteMapping(value = "/{projectId}/versions")
    public void remove(@RequestParam Long versionId, @PathVariable String projectId) {
        log.trace("deleting project version with id : " + versionId);
        versionService.remove(versionId);
    }
}
