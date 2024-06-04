package service.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String msgBody;
    private String subject;
    private String recipient;
}
