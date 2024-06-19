package service.dto;

import lombok.Data;

@Data
public class MessageDto {
    private int idMessage;
    private String username;
    private String content;
}
