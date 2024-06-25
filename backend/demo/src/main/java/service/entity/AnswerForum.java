package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "answer_forum")
public class AnswerForum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idanswer")
    private int idAnswer;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "questionid", referencedColumnName = "idquestion")
    private QuestionForum questionForum;

    @Column(name = "description")
    private String description;

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuestionForum getQuestionForum() {
        return questionForum;
    }

    public void setQuestionForum(QuestionForum questionForum) {
        this.questionForum = questionForum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}