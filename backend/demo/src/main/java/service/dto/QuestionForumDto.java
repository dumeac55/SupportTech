package service.dto;

import lombok.Data;

@Data
public class QuestionForumDto {
    private int idQuestion;
    private String title;
    private String description;
    private String username;
}
