package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "question_forum")
public class QuestionForum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquestion")
    private int idQuestion;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "iduser")
    private User user;

    @Column(name ="title")
    private String title;

    @Column(name = "description")
    private String description;

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
