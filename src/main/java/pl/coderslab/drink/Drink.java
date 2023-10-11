package pl.coderslab.drink;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.FillIngredient;
import pl.coderslab.rating.RatingEntity;
import pl.coderslab.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String method;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;

    @ManyToMany(mappedBy = "favDrinks")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<User> userFavList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "drinks_alcohol_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "alcohol_ingredient_id"))
    private List<AlcoholIngredient> alcoholIngredients;

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "drinks_fill_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "fill_ingredient_id"))
    private List<FillIngredient> fillIngredients;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "rating_id", unique=true)
    private RatingEntity rating;

    public Drink() {
    }
    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        lastUpdatedOn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public List<User> getUserFavList() {
        return userFavList;
    }

    public void setUserFavList(List<User> userFavList) {
        this.userFavList = userFavList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AlcoholIngredient> getAlcoholIngredients() {
        return alcoholIngredients;
    }

    public void setAlcoholIngredients(List<AlcoholIngredient> alcoholIngredients) {
        this.alcoholIngredients = alcoholIngredients;
    }

    public List<FillIngredient> getFillIngredients() {
        return fillIngredients;
    }

    public void setFillIngredients(List<FillIngredient> fillIngredients) {
        this.fillIngredients = fillIngredients;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }
}
