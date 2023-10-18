package pl.coderslab.rating;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;
import pl.coderslab.drink.Drink;
import pl.coderslab.user.User;

import javax.persistence.*;

@Entity
@Table(name= "Ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long valueOfRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "drink_id")
    private Drink drink;

    public RatingEntity() {
    }

    public Long getValueOfRating() {
        return valueOfRating;
    }

    public void setValueOfRating(Long valueOfRating) {
        this.valueOfRating = valueOfRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Long getId() {
        return id;
    }
}
