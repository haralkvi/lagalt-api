package no.noroff.lagalt.controller;

import no.noroff.lagalt.controllers.CommentController;
import no.noroff.lagalt.mappers.CommentMapper;
import no.noroff.lagalt.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;


    //get all

    //get all not found

    //getById

    //getById not found

    //add

    //add bad request

    //delete

    //delete bad request why not 404

    //editcomment
    @Test
    public void TestEdit_ReturnNoContent(){
        //arrange
        when(commentService.existsById(anyInt())).thenReturn(null);
        doNothing().when(commentService).updateText(anyString(),anyInt());
        //act
        ResponseEntity<?> result = commentController.editComment("Test",5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    //editcomment not found
    @Test
    public void TestEdit_ReturnNotFound(){
        //arrange
        when(commentService.existsById(anyInt())).thenReturn(null);
        doNothing().when(commentService).updateText(anyString(),anyInt());
        //act
        ResponseEntity<?> result = commentController.editComment("Test",5);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


}
