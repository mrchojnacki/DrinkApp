package pl.coderslab.drink;

import org.hibernate.annotations.Cascade;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.FillIngredient;
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
    private Double rating;
    private Long ratingCount;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;

    @ManyToMany(mappedBy = "favDrinks")
    private List<User> userFavList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "drinks_alcohol_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "alcohol_ingredient_id"))
    private List<AlcoholIngredient> alcoholIngredients;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "drinks_fill_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "fill_ingredient_id"))
    private List<FillIngredient> fillIngredients;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
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
}
