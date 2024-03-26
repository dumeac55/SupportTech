package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtype")
    private int id;

    @Column(name = "nametype")
    private String nameType;
    @Column(name = "price")
    private int price;

    public Type(String nameType, int price) {
        this.nameType = nameType;
        this.price = price;
    }
    public Type() {}
}
