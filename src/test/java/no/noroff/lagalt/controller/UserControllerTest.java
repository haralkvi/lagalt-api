package no.noroff.lagalt.controller;


import no.noroff.lagalt.controllers.UserController;
import no.noroff.lagalt.dtos.UserGetDTO;
import no.noroff.lagalt.mappers.UserMapper;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.UserService;

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
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;


    //getAll
    @Test
    public void testGetAll(){
        //Arrange
        User user = new User();
        User user2 = new User();
        String id1 = "1";
        String id2 = "2";
        String name = "John Smith";
        String email = "john@smith.com";
        String description = "John Is The Smith";
        String skill1 = "John";
        String skill2 = "Smith";

        user.setId(id1);
        user2.setId(id2);
        user.setName(name);
        user.setEmail(email);
        user.setDescription(description);

        HashSet<String> skillset = new HashSet<>();
        skillset.add(skill1);
        skillset.add(skill2);
        user.setSkillSet(skillset);

        boolean hidden = true;
        user.setHidden(hidden);

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        UserGetDTO userGetDTO = new UserGetDTO();
        userGetDTO.setId(id1);
        userGetDTO.setName(name);
        userGetDTO.setEmail(email);
        userGetDTO.setDescription(description);
        userGetDTO.setSkillSet(skillset);
        userGetDTO.setHidden(hidden);

        UserGetDTO userGetDTO1 = new UserGetDTO();
        userGetDTO1.setId(id2);

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
    public void testGetAll_error(){


    }

    //getById

    //getByID NOT FOUND




}
