package pl.coderslab.user;

import org.hibernate.annotations.Cascade;
import pl.coderslab.comments.CommentEntity;
import pl.coderslab.drink.Drink;
import pl.coderslab.rating.RatingEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Email
    private String email;

    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;


    @ManyToMany
    @JoinTable(name = "users_drinks",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Drink> favDrinks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<Drink> userMadeDrinks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<CommentEntity> listOfUserComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<RatingEntity> listOfRatings = new ArrayList<>();

    public User() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Drink> getFavDrinks() {
        return favDrinks;
    }

    public void setFavDrinks(List<Drink> favDrinks) {
        this.favDrinks = favDrinks;
    }

    public List<Drink> getUserMadeDrinks() {
        return userMadeDrinks;
    }

    public void setUserMadeDrinks(List<Drink> userMadeDrinks) {
        this.userMadeDrinks = userMadeDrinks;
    }

    public List<CommentEntity> getListOfUserComments() {
        return listOfUserComments;
    }

    public void setListOfUserComments(List<CommentEntity> listOfUserComments) {
        this.listOfUserComments = listOfUserComments;
    }

    public List<RatingEntity> getListOfRatings() {
        return listOfRatings;
    }

    public void setListOfRatings(List<RatingEntity> listOfRatings) {
        this.listOfRatings = listOfRatings;
    }
}
