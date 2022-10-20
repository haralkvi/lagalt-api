package no.noroff.lagalt.controller;

import no.noroff.lagalt.controllers.ProjectController;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.post.ProjectPostDTO;
import no.noroff.lagalt.dtos.put.ProjectPutDTO;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.ProjectCategory;
import no.noroff.lagalt.models.ProjectStatus;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
        Project project = new Project();
        ProjectGetDTO projectGetDTO = new ProjectGetDTO();

        when(projectService.findById(anyInt())).thenReturn(project);
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(projectGetDTO);

        //act
        ResponseEntity<?> result = projectController.getAll();

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    //getAll not found
    @Test
    public void TestGetAll_ReturnNotFound(){
        Project project = new Project();

        when(projectService.findById(anyInt())).thenReturn(project);
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(null);

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
        Project project = new Project();

        when(projectService.findById(anyInt())).thenReturn(project);
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(null);

        //act
        ResponseEntity<?> result = projectController.getById(5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    //add
    @Test
    public void TestAdd_ReturnCreated(){

        Project project = new Project();
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        when(projectService.existsById(anyInt())).thenReturn(true);
        when(projectService.add(any(Project.class))).thenReturn(project);

        //act
        ResponseEntity<?> result = projectController.add(projectPostDTO);

        //assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

    }

    //add bad request
    @Test
    public void TestAdd_ReturnBadRequest(){
        
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        when(projectService.existsById(anyInt())).thenReturn(true);
        when(projectService.add(any(Project.class))).thenReturn(null);

        //act
        ResponseEntity<?> result = projectController.add(projectPostDTO);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }
    @Test
    public void TestAdd_ReturnNotFound(){

        Project project = new Project();
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        when(projectService.existsById(anyInt())).thenReturn(false);
        when(projectService.add(any(Project.class))).thenReturn(project);

        //act
        ResponseEntity<?> result = projectController.add(projectPostDTO);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

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
        when(projectService.existsById(anyInt())).thenReturn(false);
        doNothing().when(projectService).deleteById(anyInt());

        //act
        ResponseEntity<?> result = projectController.delete(5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
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

        //act
        ResponseEntity<?> result = projectController.addMember(members,5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

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
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

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
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

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
