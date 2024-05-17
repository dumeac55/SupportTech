package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreview")
    private int idReview;

    @Column(name = "description")
    private String description;
    @Column(name= "grade")
    private float grade;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "idprofile")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "technicianid", referencedColumnName = "idtechnician")
    private TechnicianProfile technicianProfile;

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public TechnicianProfile getTechnicianProfile() {
        return technicianProfile;
    }

    public void setTechnicianProfile(TechnicianProfile technicianProfile) {
        this.technicianProfile = technicianProfile;
    }
}