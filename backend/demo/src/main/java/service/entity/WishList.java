package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idwishlist")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "idproduct")
    private Compari compari;

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

    public Compari getCompari() {
        return compari;
    }

    public void setCompari(Compari compari) {
        this.compari = compari;
    }
}
