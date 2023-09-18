package pl.coderslab.drink;

import pl.coderslab.user.User;

import javax.persistence.*;
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
    private int rating;

    @ManyToMany(mappedBy = "favDrinks")
    private List<User> userList = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "drinks_alcohol_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "alcohol_ingredient_id"))
    private List<AlcoholIngredient> alcoholIngredients;

    @ManyToMany
    @JoinTable(name = "drinks_fill_ingredients",
    joinColumns = @JoinColumn(name = "drink_id"),
    inverseJoinColumns = @JoinColumn(name = "fill_ingredient_id"))
    private List<FillIngredient> fillIngredients;

    public Drink() {
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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
