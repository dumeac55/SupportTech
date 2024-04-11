package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idappointment")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userprofileid", referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mechanicid", referencedColumnName = "idmechanic")
    private MechanicProfile mechanic;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "typeid", referencedColumnName = "idtype")
    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MechanicProfile getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicProfile mechanic) {
        this.mechanic = mechanic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
