package pl.coderslab.rating;

import pl.coderslab.drink.Drink;
import pl.coderslab.user.User;

import javax.persistence.*;

@Entity
@Table(name= "Ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int valueOfRating;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name = "drink_id")
    private Drink drink;

    public RatingEntity() {
    }

    public int getValueOfRating() {
        return valueOfRating;
    }

    public void setValueOfRating(int valueOfRating) {
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
