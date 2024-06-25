package service.dto;

import lombok.Data;

@Data
public class AnswerForumDto {
    private int idAnswer;
    private String description;
    private String username;
    private int idQuestion;
}