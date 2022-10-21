package no.noroff.lagalt.controller;

import no.noroff.lagalt.controllers.ApplicationController;

import no.noroff.lagalt.dtos.get.ApplicationGetDTO;
import no.noroff.lagalt.dtos.post.ApplicationPostDTO;
import no.noroff.lagalt.mappers.ApplicationMapper;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ApplicationService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ApplicationMapper applicationMapper;

    //getAll
    @Test
    public void TestGetAll_ReturnOK(){
        //arrange
        Collection<ApplicationGetDTO> applicationGetDTOs = new ArrayList<>();
        ApplicationGetDTO applicationGetDTO = new ApplicationGetDTO();
        applicationGetDTOs.add(applicationGetDTO);
        Collection<Application> applications = new ArrayList<>();

        when(applicationMapper.applicationToApplicationDTO(anyCollection())).thenReturn(applicationGetDTOs);
        when(applicationService.findAll()).thenReturn(applications);

        //act
        ResponseEntity<?> result = applicationController.getAll();

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //getAll not found
    @Test
    public void TestGetAll_ReturnNotFound(){
        //arrange
        Collection<ApplicationGetDTO> applicationGetDTO = new ArrayList<>();

        when(applicationMapper.applicationToApplicationDTO(anyCollection())).thenReturn(applicationGetDTO);
        when(applicationService.findAll()).thenReturn(null);

        //act
        ResponseEntity<?> result = applicationController.getAll();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //getById
    @Test
    public void TestGetById_ReturnOK(){
        //arrange
        ApplicationGetDTO applicationGetDTO = new ApplicationGetDTO();
        applicationGetDTO.setApplication_id(1);
        Application application = new Application();
        application.setApplication_id(1);

        when(applicationMapper.applicationToApplicationDTO(any(Application.class))).thenReturn(applicationGetDTO);
        when(applicationService.findById(anyInt())).thenReturn(application);

        //act
        ResponseEntity<?> result = applicationController.getById(1);

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    //getById not found
    @Test
    public void TestGetById_ReturnNotFound(){
        //arrange
        ApplicationGetDTO applicationGetDTO = new ApplicationGetDTO();
        applicationGetDTO.setApplication_id(1);
        Application application = new Application();
        application.setApplication_id(2);

        when(applicationMapper.applicationToApplicationDTO(any(Application.class))).thenReturn(null);
        when(applicationService.findById(anyInt())).thenReturn(application);

        //act
        ResponseEntity<?> result = applicationController.getById(3);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //add

    @Test
    public void TestAdd_ReturnCreated(){
        //arrange
        ApplicationPostDTO inputApplication = new ApplicationPostDTO();
        User user = new User();
        Project project = new Project();
        Application application = new Application();
        application.setProject(project);
        application.setUser(user);
        when(applicationMapper.applicationPostDTOtoApplication(any(ApplicationPostDTO.class))).thenReturn(application);
        when(applicationService.add(any(Application.class))).thenReturn(application);

        //act
        ResponseEntity<?> result = applicationController.add(inputApplication);

        //assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    //add not found
    @Test
    public void TestAdd_ReturnNotFound(){
        //arrange
        ApplicationPostDTO inputApplication = new ApplicationPostDTO();
        User user = new User();
        Project project = new Project();
        Application application = new Application();
        application.setProject(project);
        application.setUser(user);
        when(applicationMapper.applicationPostDTOtoApplication(any(ApplicationPostDTO.class))).thenReturn(application);
        when(applicationService.add(any(Application.class))).thenReturn(null);

        //act
        ResponseEntity<?> result = applicationController.add(inputApplication);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //update
    @Test
    public void TestUpdate_ReturnNoContent(){
        //arrange
        Application application = new Application();
        application.setApplication_id(1);
        when(applicationService.update(any(Application.class))).thenReturn(application);

        //act
        ResponseEntity<?> result = applicationController.update(application,1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //update bad request

    @Test
    public void TestUpdate_ReturnBadRequest(){
        //arrange
        Application application = new Application();
        application.setApplication_id(1);
        when(applicationService.update(any(Application.class))).thenReturn(application);

        //act
        ResponseEntity<?> result = applicationController.update(application,2);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    //delete
    @Test
    public void TestDelete_ReturnNoContent(){
        //arrange
        doNothing().when(applicationService).deleteById(anyInt());

        //act
        ResponseEntity<?> result = applicationController.delete(1);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //delete not found
    @Test
    public void TestDelete_ReturnNotBadRequest(){
        //arrange
        doNothing().when(applicationService).deleteById(anyInt());

        //act
        ResponseEntity<?> result = applicationController.delete(0);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

}


