package pl.coderslab.drink;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.coderslab.comments.CommentEntity;
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
    @Column(columnDefinition="TEXT")
    private String method;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;

    @ManyToMany(mappedBy = "favDrinks",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private List<User> userFavList = new ArrayList<>();

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "drinks_alcohol_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "alcohol_ingredient_id"))
    private List<AlcoholIngredient> alcoholIngredients;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "drinks_fill_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "fill_ingredient_id"))
    private List<FillIngredient> fillIngredients;

    @OneToMany(mappedBy = "drink",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    List<RatingEntity> listOfRatings = new ArrayList<>();

    @OneToMany(mappedBy = "drink",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    List<CommentEntity> listOfDrinkComments = new ArrayList<>();

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

    public List<CommentEntity> getListOfDrinkComments() {
        return listOfDrinkComments;
    }

    public void setListOfDrinkComments(List<CommentEntity> listOfDrinkComments) {
        this.listOfDrinkComments = listOfDrinkComments;
    }

    public List<FillIngredient> getFillIngredients() {
        return fillIngredients;
    }

    public void setFillIngredients(List<FillIngredient> fillIngredients) {
        this.fillIngredients = fillIngredients;
    }

    public List<RatingEntity> getListOfRatings() {
        return listOfRatings;
    }

    public void setListOfRatings(List<RatingEntity> listOfRatings) {
        this.listOfRatings = listOfRatings;
    }
}
