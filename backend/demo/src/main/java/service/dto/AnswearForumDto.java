package service.dto;

import lombok.Data;

@Data
public class AnswearForumDto {
    private int idAnswear;
    private String description;
    private String username;
    private int idQuestion;
}