package service.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private String description;
    private float grade;
    private float avgGrade;
    private int userId;
    private int mechanicId;
}
