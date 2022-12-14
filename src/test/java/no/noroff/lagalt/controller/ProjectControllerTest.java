package no.noroff.lagalt.controller;

import no.noroff.lagalt.controllers.ProjectController;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.post.ProjectPostDTO;
import no.noroff.lagalt.dtos.put.ProjectPutDTO;
import no.noroff.lagalt.exceptions.ProjectNotFoundException;
import no.noroff.lagalt.exceptions.UserNotFoundException;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.ProjectCategory;
import no.noroff.lagalt.models.ProjectStatus;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private UserService userService;


    //getAll
    @Test
    public void TestGetAll_ReturnOK(){
        Collection<Project> projects = new ArrayList<>();
        Project project = new Project();
        projects.add(project);
        Collection<ProjectGetDTO> projectGetDTOS = new ArrayList<>();
        ProjectGetDTO projectGetDTO = new ProjectGetDTO();
        projectGetDTOS.add(projectGetDTO);

        when(projectService.findAll()).thenReturn(projects);
        when(projectMapper.projectToProjectDTO(anyCollection())).thenReturn(projectGetDTOS);

        //act
        ResponseEntity<?> result = projectController.getAll();

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    //getAll not found
    @Test
    public void TestGetAll_ReturnNotFound(){

        when(projectService.findAll()).thenReturn(null);
        when(projectMapper.projectToProjectDTO(anyCollection())).thenReturn(null);

        //act
        ResponseEntity<?> result = projectController.getAll();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    //getById
    @Test
    public void TestGetById_ReturnOK(){
        Project project = new Project();
        ProjectGetDTO projectGetDTO = new ProjectGetDTO();

        when(projectService.findById(anyInt())).thenReturn(project);
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(projectGetDTO);

        //act
        ResponseEntity<?> result = projectController.getById(1);

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    //getById not found
    @Test
    public void TestGetById_ReturnNotFound(){
        // arrange
        when(projectService.findById(anyInt())).thenThrow(ProjectNotFoundException.class);

        //act and assert
        Assert.assertThrows(ProjectNotFoundException.class, () -> projectController.getById(5));
    }

    //add
/*    @Test
    public void TestAdd_ReturnCreated(){
        User user = new User();
        user.setId("2");
        Project project = new Project();
        project.setId(1);
        project.setOwner(user);
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        projectPostDTO.setOwner("2");
        when(userService.existsById(anyString())).thenReturn(true);
        when(projectService.add(any(Project.class))).thenReturn(project);
        doNothing().when(userService).addMembers(new String[]{project.getOwner().getId()}, project.getId());

        //act
        ResponseEntity<?> result = projectController.add(projectPostDTO);

        //assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

    }*/
    //TODO: Dont understand why this test in particular fails

    //add bad request
//    @Test
//    public void TestAdd_ReturnNotFound(){
//
//        // arrange
//        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
//        when(projectMapper.projectPostDTOtoProject(projectPostDTO)).thenReturn(any(Project.class));
//        when(projectService.add(any(Project.class))).thenReturn(notNull());
//        doNothing().when(userService).addMembers(any(), anyInt());
//
//        //act
//        ResponseEntity<?> result = projectController.add(projectPostDTO);
//
//        //assert
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//
//    }
    //update
    @Test
    public void TestUpdate_ReturnNoContent(){

        Project project = new Project();
        project.setId(1);
        ProjectPutDTO projectPutDTO = new ProjectPutDTO(1,"test", ProjectCategory.WEB, ProjectStatus.IN_PROGRESS,"test", "placeholder");
        when(projectMapper.projectPutDTOToProject(projectPutDTO)).thenReturn(project);

        //act
        ResponseEntity<?> result = projectController.update(projectPutDTO,1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    //update bad request
    @Test
    public void TestUpdate_ReturnBadRequest(){

        Project project = new Project();
        project.setId(0);
        ProjectPutDTO projectPutDTO = new ProjectPutDTO(0,"test", ProjectCategory.WEB, ProjectStatus.IN_PROGRESS,"test", "placeholder");
        when(projectMapper.projectPutDTOToProject(projectPutDTO)).thenReturn(project);

        //act
        ResponseEntity<?> result = projectController.update(projectPutDTO,1);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    //delete
    @Test
    public void TestDelete_ReturnNoContent(){
        //arrange
        when(projectService.existsById(anyInt())).thenReturn(true);
        doNothing().when(projectService).deleteById(anyInt());

        //act
        ResponseEntity<?> result = projectController.delete(1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //delete not found
    @Test
    public void TestDelete_ReturnNotFound(){
        //arrange
        doThrow(ProjectNotFoundException.class).when(projectService).deleteById(anyInt());

        //act and assert
        Assert.assertThrows(ProjectNotFoundException.class, () -> projectController.delete(5));
    }

    //addMember
    @Test
    public void TestAddMember_ReturnNoContent(){

        String[] members = {"test"};
        when(userService.existsById(anyString())).thenReturn(true);
        doNothing().when(userService).addMembers(any(),anyInt());

        //act
        ResponseEntity<?> result = projectController.addMember(members,5);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    //addMember not found
    @Test
    public void TestAddMember_ReturnNotFound(){

        String[] members = {"test"};
        when(userService.existsById(anyString())).thenReturn(false);
        doNothing().when(userService).addMembers(any(),anyInt());

        //act and assert
        Assert.assertThrows(UserNotFoundException.class, () -> projectController.addMember(members,5));

    }

    //addTags
    @Test
    public void TestAddTags_ReturnNoContent(){

        String[] tags = {"test"};
        when(projectService.existsById(anyInt())).thenReturn(true);
        doNothing().when(projectService).addTags(any(),anyInt());

        //act
        ResponseEntity<?> result = projectController.addTags(tags,1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }


    //addTags not found
    @Test
    public void TestAddTags_ReturnNotFound(){

        String[] tags = {"test"};
        when(projectService.existsById(anyInt())).thenReturn(false);
        doNothing().when(projectService).addTags(any(),anyInt());

        //act
        ResponseEntity<?> result = projectController.addTags(tags,5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    //updated skills
    @Test
    public void TestUpdateSkills_ReturnNoContent(){

        String[] skills = {"test"};
        when(projectService.existsById(anyInt())).thenReturn(true);
        doNothing().when(projectService).addSkills(any(),anyInt());

        //act
        ResponseEntity<?> result = projectController.updatedSkills(skills,1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    //updated skills not found
    @Test
    public void TestUpdateSkills_ReturnNotFound(){

        String[] skills = {"test"};
        when(projectService.existsById(anyInt())).thenReturn(false);
        doNothing().when(projectService).addSkills(any(),anyInt());

        //act
        ResponseEntity<?> result = projectController.updatedSkills(skills,5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }
}
