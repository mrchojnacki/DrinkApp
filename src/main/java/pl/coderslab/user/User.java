package pl.coderslab.user;

import pl.coderslab.drink.Drink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToMany
    @JoinTable(name = "drinks_alcohol_ingredients",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "alcohol_ingredient_id"))
    private List<Drink> favDrinks = new ArrayList<>();


}
