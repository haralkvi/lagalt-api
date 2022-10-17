package no.noroff.lagalt.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link no.noroff.lagalt.models.Comment} entity
 */
@Data
public class CommentDetails implements Serializable {
    private final int id;
    private final String text;
    private final String time;
}