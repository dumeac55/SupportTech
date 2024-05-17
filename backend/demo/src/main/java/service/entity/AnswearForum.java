package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "answear_forum")
public class AnswearForum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idanswear")
    private int idAnswear;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "questionid", referencedColumnName = "idquestion")
    private QuestionForum questionForum;

    @Column(name = "description")
    private String description;

    public int getIdAnswear() {
        return idAnswear;
    }

    public void setIdAnswear(int idAnswear) {
        this.idAnswear = idAnswear;
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