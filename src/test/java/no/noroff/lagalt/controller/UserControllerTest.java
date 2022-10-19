package no.noroff.lagalt.controller;


import no.noroff.lagalt.controllers.UserController;
import no.noroff.lagalt.dtos.get.UserGetDTO;
import no.noroff.lagalt.dtos.post.UserPostDTO;
import no.noroff.lagalt.mappers.UserMapper;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.models.UserPutDTO;
import no.noroff.lagalt.services.UserService;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    String id = "1";
    String name = "John Smith";
    String email = "john@smith.com";
    String description = "John Is The Smith";
    String skill1 = "John";
    String skill2 = "Smith";
    List<User> users = new ArrayList<>();
    HashSet<String> skillset = new HashSet<>();
    UserGetDTO userGetDTO = new UserGetDTO();
    boolean hidden;
    User user = new User();

    @Before
    public void init(){

        users.clear();

        user.setName(name);
        user.setEmail(email);
        user.setDescription(description);
        user.setId(id);

        skillset.add(skill1);
        skillset.add(skill2);
        user.setSkillSet(skillset);

        userGetDTO.setId(id);
        userGetDTO.setName(name);
        userGetDTO.setEmail(email);
        userGetDTO.setDescription(description);
        userGetDTO.setSkillSet(skillset);

        user.setHidden(hidden);
        users.add(user);

    }

    //getAll
    @Test
    public void TestGetAll_ReturnOK(){
        //Arrange

        User user2 = new User();
        user2.setId("2");
        users.add(user2);

        UserGetDTO userGetDTO1 = new UserGetDTO();
        userGetDTO1.setId("2");
        userGetDTO1.setHidden(true);

        Collection<UserGetDTO> list = new ArrayList<>();
        list.add(userGetDTO);
        list.add(userGetDTO1);

        when(userService.findAll()).thenReturn(users);
        when(userMapper.userToUserDTO(users)).thenReturn(list);

        //Act
        ResponseEntity<?> result = userController.getAll();

        //Assert
        assertEquals(list, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //getAll NOT FOUND
    @Test
    public void TestGetAll_ReturnNotFound(){

        Collection<UserGetDTO> list = new ArrayList<>();
        Collection<User> listUsers = new ArrayList<>();

        when(userService.findAll()).thenReturn(listUsers);
        when(userMapper.userToUserDTO(listUsers)).thenReturn(list);

        ResponseEntity<?> result = userController.getAll();

        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //getById
    @Test
    public void TestGetById_ReturnOK(){
        when(userService.findById(anyString())).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userGetDTO);

        //Act
        ResponseEntity<?> result = userController.getById("1");

        //Assert
        assertEquals(userGetDTO, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    //getByID NOT FOUND
    @Test
    public void TestGetById_ReturnNotFound(){
        when(userService.findById(anyString())).thenReturn(null);
        when(userMapper.userToUserDTO((User) null)).thenReturn(null);

        //Act
        ResponseEntity<?> result = userController.getById("3");

        //Assert
        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());




    }

    //add
    @Test
    public void TestAdd_ReturnCreated(){
        //arrange
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setId(id);
        userPostDTO.setName(name);
        userPostDTO.setEmail(email);
        userPostDTO.setHidden(hidden);

        when(userMapper.userPostDTOtoUser(userPostDTO)).thenReturn(user);
        when(userService.add(user)).thenReturn(user);

        //act
        ResponseEntity<?> result = userController.add(userPostDTO);

        //assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    //add 400
    @Test
    public void TestAdd_ReturnBadRequest(){

        UserPostDTO userPostDTO = new UserPostDTO();

        when(userMapper.userPostDTOtoUser(userPostDTO)).thenReturn(user);

        //act
        ResponseEntity<?> result = userController.add(userPostDTO);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    //update
    @Test
    public void TestUpdate_ReturnNoContent(){
        //arrange
        UserPutDTO userPutDTO = new UserPutDTO(id,name,email);

        when(userMapper.userPutDTOtoUser(userPutDTO)).thenReturn(user);
        when(userService.update(user)).thenReturn(user);

        //act
        ResponseEntity<?> result = userController.update(userPutDTO,id);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //update 400
    @Test
    public void TestUpdate_ReturnBadRequest(){

        UserPutDTO userPutDTO = new UserPutDTO("0",null,null);
        when(userMapper.userPutDTOtoUser(userPutDTO)).thenReturn(user);

        //act
        ResponseEntity<?> result = userController.update(userPutDTO, id);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    //updateSkillset
    @Test
    public void TestUpdateSkillset_ReturnOK(){
        //arrange
        String[] skillset = {skill1,skill2};
        when(userService.existsById(anyString())).thenReturn(true);
        doNothing().when(userService).updateSkillset(any(),anyString());

        //act
        ResponseEntity<?> result = userController.updateSkillset(skillset,id);

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    //updateSkillset 400
    @Test
    public void TestUpdateSkillset_ReturnBadRequest(){
        String[] skillset = {};
        when(userService.existsById(anyString())).thenReturn(false);
        doNothing().when(userService).updateSkillset(any(),anyString());

        //act
        ResponseEntity<?> result = userController.updateSkillset(skillset,"4");

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    //addToClickHistory
    @Test
    public void TestAddToClickHistory_ReturnOK(){
        //arrange
        int projectId = 1;
        when(userService.addToClickHistory(anyInt(),anyString())).thenReturn(true);

        //act
        ResponseEntity<?> result = userController.addToClickHistory(projectId,id);

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //addToClickHistory 400
    @Test
    public void TestAddToClickHistory_ReturnNotFound(){
        //arrange
        int projectId = 4;
        when(userService.addToClickHistory(anyInt(),anyString())).thenReturn(false);

        //act
        ResponseEntity<?> result = userController.addToClickHistory(projectId,id);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //changeDescription
    @Test
    public void TestChangeDescription_ReturnOK(){
        //arrange
        String[] description = {"test"};
        doNothing().when(userService).changeDescription(any(),anyString());

        //act
        ResponseEntity<?> result = userController.changeDescription(description,id);

        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //changeDescription 400
    @Test
    public void TestChangeDescription_ReturnBadRequest(){
        //arrange
        String[] descriptionEmpty = {};
        doNothing().when(userService).changeDescription(any(),anyString());

        //act
        ResponseEntity<?> result = userController.changeDescription(descriptionEmpty,id);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    //delete
    @Test
    public void TestDelete_ReturnNoContent(){
        //arrange
        when(userService.findById(anyString())).thenReturn(user);
        doNothing().when(userService).deleteById(anyString());

        //act
        ResponseEntity<?> result = userController.delete(id);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //delete 404
    @Test
    public void TestDelete_ReturnNotFound(){
        //arrange
        when(userService.findById(anyString())).thenReturn(null);
        doNothing().when(userService).deleteById(anyString());

        //act
        ResponseEntity<?> result = userController.delete("4");

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
