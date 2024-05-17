package service.dto;

public class AnswearForumDto {
    private int idAnswear;
    private String description;
    private String username;
    private int idQuestion;

    public int getIdAnswear() {
        return idAnswear;
    }

    public void setIdAnswear(int idAnswear) {
        this.idAnswear = idAnswear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
}