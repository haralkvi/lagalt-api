package no.noroff.lagalt.controller;

import no.noroff.lagalt.controllers.CommentController;
import no.noroff.lagalt.dtos.get.CommentGetDTO;
import no.noroff.lagalt.dtos.post.CommentPostDTO;
import no.noroff.lagalt.exceptions.CommentNotFoundException;
import no.noroff.lagalt.mappers.CommentMapper;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.services.CommentService;
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

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;


    //get all
    @Test
    public void TestGetAll_ReturnOK(){
        //arrange
        Comment comment = new Comment();
        Collection<Comment> comments = new ArrayList<>();
        comments.add(comment);
        CommentGetDTO commentGetDTO = new CommentGetDTO();
        Collection<CommentGetDTO> dtos = new ArrayList<>();
        dtos.add(commentGetDTO);

        when(commentMapper.commentToCommentDTO(anyCollection())).thenReturn(dtos);
        when(commentService.findAll()).thenReturn(comments);
        //act
        ResponseEntity<?> result = commentController.getAll();
        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    //get all not found
    @Test
    public void TestAll_ReturnNotFound(){
        //arrange
        Collection<Comment> comments = new ArrayList<>();
        Collection<CommentGetDTO> dtos = new ArrayList<>();

        when(commentMapper.commentToCommentDTO(anyCollection())).thenReturn(dtos);
        when(commentService.findAll()).thenReturn(comments);
        //act
        ResponseEntity<?> result = commentController.getAll();
        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //getById
    @Test
    public void TestGetById_ReturnOK(){
        //arrange
        CommentGetDTO commentGetDTO = new CommentGetDTO();
        Comment comment = new Comment();

        when(commentMapper.commentToCommentDTO(any(Comment.class))).thenReturn(commentGetDTO);
        when(commentService.findById(anyInt())).thenReturn(comment);
        //act
        ResponseEntity<?> result = commentController.getById(1);
        //assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    //getById not found
    @Test
    public void TestGetById_ReturnNotFound(){
        //arrange
        Comment comment = new Comment();

        when(commentMapper.commentToCommentDTO(any(Comment.class))).thenReturn(null);
        when(commentService.findById(anyInt())).thenReturn(comment);
        //act
        ResponseEntity<?> result = commentController.getById(5);
        //assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    //add
    @Test
    public void TestAdd_ReturnCreated(){
        //arrange
        CommentPostDTO commentPostDTO = new CommentPostDTO();
        commentPostDTO.setUser("1");
        commentPostDTO.setProject(1);
        Comment comment = new Comment();
        comment.setId(1);

        when(userService.existsById(anyString())).thenReturn(true);
        when(projectService.existsById(anyInt())).thenReturn(true);
        when(commentMapper.commentPostDTOtoComment(any(CommentPostDTO.class))).thenReturn(comment);
        when(commentService.add(any(Comment.class))).thenReturn(comment);
        //act
        ResponseEntity<?> result = commentController.add(commentPostDTO);
        //assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
//
//    //add bad request
//    @Test
//    public void TestAdd_ReturnBadRequest(){
//        //arrange
//        CommentPostDTO commentPostDTO = new CommentPostDTO();
//        Comment comment = new Comment();
//
//        when(userService.existsById(anyString())).thenReturn(false);
//        when(projectService.existsById(anyInt())).thenReturn(false);
//        when(commentMapper.commentPostDTOtoComment(any(CommentPostDTO.class))).thenReturn(comment);
//        when(commentService.add(any(Comment.class))).thenReturn(comment);
//        //act
//        ResponseEntity<?> result = commentController.add(commentPostDTO);
//        //assert
//        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
//    }

    //delete
    @Test
    public void TestDelete_ReturnNoContent(){
        //arrange
        doNothing().when(commentService).deleteById(anyInt());
        when(commentService.existsById(anyInt())).thenReturn(true);
        //act
        ResponseEntity<?> result = commentController.delete(5);
        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //delete bad request TODO: why not 404
    @Test
    public void TestDelete_ReturnNotFound(){
        //arrange
        doNothing().when(commentService).deleteById(anyInt());
        when(commentService.existsById(anyInt())).thenReturn(false);

        //act and assert
        Assert.assertThrows(CommentNotFoundException.class, () -> commentController.delete(0));
    }


    //editcomment
    @Test
    public void TestEdit_ReturnNoContent(){
        //arrange
        when(commentService.existsById(anyInt())).thenReturn(true);
        doNothing().when(commentService).updateText(anyString(),anyInt());
        //act
        ResponseEntity<?> result = commentController.editComment("Test",5);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }


    //editcomment not found
    @Test
    public void TestEdit_ReturnNotFound(){
        //arrange
        when(commentService.existsById(anyInt())).thenReturn(false);
        doNothing().when(commentService).updateText(anyString(),anyInt());

        //act and assert
        Assert.assertThrows(CommentNotFoundException.class, () -> commentController.editComment("Test", 5));
    }
}
